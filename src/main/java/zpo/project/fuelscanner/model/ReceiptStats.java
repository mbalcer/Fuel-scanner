package zpo.project.fuelscanner.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.YearMonth;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceiptStats {

    YearMonth yearMonth;
    double litres;
    double cost;
    double averageCostPerLitre;
    double minCostPerLitre;
    double maxCostPerLitre;

    public ReceiptStats(YearMonth yearMonth, double litres, double cost, double minCostPerLitre, double maxCostPerLitre) {
        this.yearMonth = yearMonth;
        this.litres = litres;
        this.cost = cost;
        this.minCostPerLitre = minCostPerLitre;
        this.maxCostPerLitre = maxCostPerLitre;
        this.averageCostPerLitre = cost / litres;
    }
}
