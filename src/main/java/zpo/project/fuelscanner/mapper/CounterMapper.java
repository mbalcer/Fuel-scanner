package zpo.project.fuelscanner.mapper;

import org.springframework.stereotype.Component;
import zpo.project.fuelscanner.dto.CounterDTO;
import zpo.project.fuelscanner.model.Counter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@Component
public class CounterMapper {

    public Counter convertToEntity(CounterDTO counterDTO) {
        Instant instant = Instant.ofEpochMilli(Long.parseLong(counterDTO.getCounterLocalDate()));
        LocalDate date = instant.atZone(ZoneId.of("Poland")).toLocalDate();

        return new Counter(0l, counterDTO.getCounterState(), date, counterDTO.getFuelTank(), counterDTO.getUser());
    }
}
