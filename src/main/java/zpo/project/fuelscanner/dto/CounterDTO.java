package zpo.project.fuelscanner.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zpo.project.fuelscanner.model.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CounterDTO {
    private Double counterState;
    private String counterLocalDate;
    private Double fuelTank;
    private User user;
}
