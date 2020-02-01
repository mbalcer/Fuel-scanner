package zpo.project.fuelscanner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zpo.project.fuelscanner.model.*;
import zpo.project.fuelscanner.repository.CounterRepository;
import zpo.project.fuelscanner.repository.ReceiptRepository;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatsService {

    private ReceiptRepository receiptRepository;
    private CounterRepository counterRepository;

    @Autowired
    public StatsService(ReceiptRepository receiptRepository, CounterRepository counterRepository) {
        this.receiptRepository = receiptRepository;
        this.counterRepository = counterRepository;
    }

    public double getAllReceiptLitresByUser(User user) {
        return receiptRepository.findAllByUser(user)
                .stream()
                .mapToDouble(receipt ->
                        receipt.getLitres())
                .sum();
    }

    public double getAllReceiptCostByUser(User user) {
        return receiptRepository.findAllByUser(user)
                .stream()
                .mapToDouble(receipt ->
                        receipt.getCost())
                .sum();
    }

    public List<ReceiptStats> getReceiptStatsyByUserGroupedByYearMonth(User user) {
        return receiptRepository.findAllByUser(user)
                .stream()
                .collect(Collectors.groupingBy(receipt -> YearMonth.of(receipt.getReceiptLocalDate().getYear(), receipt.getReceiptLocalDate().getMonth())))
                .entrySet()
                .stream()
                .map(o -> new ReceiptStats(o.getKey(),
                        o.getValue()
                                .stream()
                                .mapToDouble(value -> value.getLitres())
                                .sum(),
                        o.getValue()
                                .stream()
                                .mapToDouble(value -> value.getCost())
                                .sum(),
                        o.getValue()
                                .stream()
                                .mapToDouble(value -> (value.getCost() / value.getLitres()))
                                .min().orElse(0),
                        o.getValue()
                                .stream()
                                .mapToDouble(value -> (value.getCost() / value.getLitres()))
                                .max().orElse(0)))
                .collect(Collectors.toList());
    }

    public List<Receipt> getAllReceiptByUserAndReceiptLocalDateBetween(User user, LocalDate start, LocalDate end) {
        return receiptRepository.findAllByUserAndReceiptLocalDateBetween(user, start, end);
    }


    public List<CounterStats> getCounterStatsByUser(User user) {
        ArrayList<CounterStats> counterStats = new ArrayList<>();
        counterRepository.findAllByUser(user)
                .stream()
                .sorted(Comparator.comparing(Counter::getCounterLocalDate))
                .reduce((counter, counter2) -> {

                    List<Receipt> receipts = receiptRepository.findAllByUserAndReceiptLocalDateBetween(
                            user, counter.getCounterLocalDate(), counter2.getCounterLocalDate());

                    CounterStats cs = new CounterStats(counter.getCounterLocalDate(),
                            counter2.getCounterLocalDate(),
                            counter2.getCounterState() - counter.getCounterState(),
                            counter.getFuelTank() - counter2.getFuelTank()
                                    + receipts.stream()
                                    .mapToDouble(Receipt::getLitres).sum(),
                            receipts.stream()
                                    .mapToDouble(Receipt::getLitres).sum(),
                            receipts.stream()
                                    .mapToDouble(Receipt::getCost).sum());

                    //TODO What if user forgot to add receipt(he has now more fuel in tank than earlier) -> Fuel consumed will be negative
                    //Example: InitService -> counter 11
                    if (cs.getFuelConsumed() < 0 || cs.getDistanceTravelled() < 0) {

                    }

                    counterStats.add(cs);

                    return counter2;
                });

        return counterStats;
    }

}
