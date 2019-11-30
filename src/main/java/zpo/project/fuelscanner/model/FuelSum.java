package zpo.project.fuelscanner.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FuelSum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Double Litres;
    Double Cost;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLitres() {
        return Litres;
    }

    public void setLitres(Double litres) {
        Litres = litres;
    }

    public Double getCost() {
        return Cost;
    }

    public void setCost(Double cost) {
        Cost = cost;
    }



}
