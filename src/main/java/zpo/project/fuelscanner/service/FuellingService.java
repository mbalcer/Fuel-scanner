package zpo.project.fuelscanner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import zpo.project.fuelscanner.model.FuellingDateSummary;
import zpo.project.fuelscanner.model.Fuelling;
import zpo.project.fuelscanner.model.User;
import zpo.project.fuelscanner.repository.FuellingRepository;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FuellingService {

    @Autowired
    FuellingRepository fuellingRepository;

    public List<Fuelling> getFuellings() {
        return fuellingRepository.findAll(Sort.by(Sort.Order.desc("id")));
    }

    public Fuelling getFuelling(long id) {
        return fuellingRepository.findById(id).get();
    }

    public Fuelling createFuelling(Fuelling fuelling) {
        return fuellingRepository.save(fuelling);
    }

    public void updateFuelling(Fuelling fuelling) {
        fuellingRepository.save(fuelling);
    }

    public void deleteFuelling(long id) {
        fuellingRepository.deleteById(id);
    }


    //Stats
    public double getAllFuelLitresByUser(User user) {
        return fuellingRepository.findAllByUser(user)
                .stream()
                .mapToDouble(fuelling -> fuelling
                        .getFuelSum()
                        .getLitres())
                .sum();
    }

    public double getAllFuelCostByUser(User user) {
        return fuellingRepository.findAllByUser(user)
                .stream()
                .mapToDouble(fuelling -> fuelling
                        .getFuelSum()
                        .getCost())
                .sum();
    }

    public List<FuellingDateSummary> getFuellingDateSummaryByUserGroupedByYearMonth(User user) {
        return fuellingRepository.findAllByUser(user)
                .stream()
                .collect(Collectors.groupingBy(fuelling -> YearMonth.of(fuelling.getFuellingLocalDate().getYear(), fuelling.getFuellingLocalDate().getMonth())))
                .entrySet()
                .stream()
                .map(o -> new FuellingDateSummary(o.getKey(),
                        o.getValue()
                                .stream()
                                .mapToDouble(value -> value.getFuelSum().getLitres())
                                .sum(),
                        o.getValue()
                                .stream()
                                .mapToDouble(value -> value.getFuelSum().getCost())
                                .sum(),
                        o.getValue()
                                .stream()
                                .mapToDouble(value -> (value.getFuelSum().getCost() / value.getFuelSum().getLitres()))
                                .min().orElse(0),
                        o.getValue()
                                .stream()
                                .mapToDouble(value -> (value.getFuelSum().getCost() / value.getFuelSum().getLitres()))
                                .max().orElse(0)))
                .collect(Collectors.toList());
    }

    public List<Fuelling> getAllFuellingByUserAndFuellingLocalDateBetween(User user, LocalDate start, LocalDate end) {
        return fuellingRepository.findAllByUserAndFuellingLocalDateBetween(user, start, end);
    }

}
