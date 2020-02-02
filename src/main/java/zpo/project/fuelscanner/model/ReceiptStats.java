package zpo.project.fuelscanner.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.YearMonth;
import java.util.Locale;

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

    public static ReceiptStats roundTo2DecimalPlaces(ReceiptStats receiptStats) {
        DecimalFormat df = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.ENGLISH));

        receiptStats.setLitres(Double.valueOf(df.format(receiptStats.getLitres())));
        receiptStats.setCost(Double.valueOf(df.format(receiptStats.getCost())));
        receiptStats.setAverageCostPerLitre(Double.valueOf(df.format(receiptStats.getAverageCostPerLitre())));
        receiptStats.setMinCostPerLitre(Double.valueOf(df.format(receiptStats.getMinCostPerLitre())));
        receiptStats.setMaxCostPerLitre(Double.valueOf(df.format(receiptStats.getMaxCostPerLitre())));

        return receiptStats;
    }
}
