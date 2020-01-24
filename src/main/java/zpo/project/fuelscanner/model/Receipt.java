package zpo.project.fuelscanner.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Receipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    private String url;

    @Column(columnDefinition="varchar(1000)")
    private String content;

    private LocalDate receiptLocalDate;
    private Double litres;
    private Double pricePerLitres;
    private Double cost;

    @ManyToOne
    private  User user;
}
