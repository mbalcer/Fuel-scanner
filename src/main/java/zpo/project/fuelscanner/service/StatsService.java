package zpo.project.fuelscanner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zpo.project.fuelscanner.model.Receipt;
import zpo.project.fuelscanner.model.ReceiptStats;
import zpo.project.fuelscanner.model.User;
import zpo.project.fuelscanner.repository.ReceiptRepository;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatsService {

    private ReceiptRepository receiptRepository;

    @Autowired
    public StatsService(ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
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
}
