package zpo.project.fuelscanner.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.util.Locale;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CounterStats {

    LocalDate startLocalDate;
    LocalDate endLocalDate;

    double distanceTravelled;
    double fuelConsumed;
    double avgFuelConsumedOn100km;

    double litresBought;
    double costLitresBought;
    double averageCostPerLitre;

    public CounterStats(LocalDate startLocalDate, LocalDate endLocalDate,
                        double distanceTravelled, double fuelConsumed, double litresBought, double costLitresBought) {
        this.startLocalDate = startLocalDate;
        this.endLocalDate = endLocalDate;
        this.distanceTravelled = distanceTravelled;
        this.fuelConsumed = fuelConsumed;
        this.litresBought = litresBought;
        this.costLitresBought = costLitresBought;

        //Average is calculated based on previous values
        if (distanceTravelled > 0 && fuelConsumed > 0) {
            this.avgFuelConsumedOn100km = (fuelConsumed / distanceTravelled) * 100;
        } else {
            this.avgFuelConsumedOn100km = 0.0;
        }

        if (litresBought > 0) {
            this.averageCostPerLitre = costLitresBought / litresBought;
        } else {
            this.averageCostPerLitre = 0.0;
        }
    }

    public static CounterStats roundTo2DecimalPlaces(CounterStats counterStats) {
        DecimalFormat df = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.ENGLISH));

        counterStats.setDistanceTravelled(Double.valueOf(df.format(counterStats.getDistanceTravelled())));
        counterStats.setFuelConsumed(Double.valueOf(df.format(counterStats.getFuelConsumed())));
        counterStats.setAvgFuelConsumedOn100km(Double.valueOf(df.format(counterStats.getAvgFuelConsumedOn100km())));
        counterStats.setLitresBought(Double.valueOf(df.format(counterStats.getLitresBought())));
        counterStats.setCostLitresBought(Double.valueOf(df.format(counterStats.getCostLitresBought())));
        counterStats.setAverageCostPerLitre(Double.valueOf(df.format(counterStats.getAverageCostPerLitre())));

        return counterStats;
    }
}

