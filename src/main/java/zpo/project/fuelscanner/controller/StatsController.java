package zpo.project.fuelscanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zpo.project.fuelscanner.model.CounterStats;
import zpo.project.fuelscanner.model.ReceiptStats;
import zpo.project.fuelscanner.service.StatsService;
import zpo.project.fuelscanner.service.UserService;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/stats")
@CrossOrigin
public class StatsController {

    private StatsService statsService;
    private UserService userService;
    DecimalFormat df;

    @Autowired
    public StatsController(StatsService statsService, UserService userService) {
        this.statsService = statsService;
        this.userService = userService;

        //Round to 2 decimal places
        df = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.ENGLISH));
    }

    @GetMapping("/receiptStats/{login}")
    public List<ReceiptStats> getReceiptStatsByUserLogin(@PathVariable String login) {
        return statsService.getReceiptStatsyByUserGroupedByYearMonth(userService.getUserByLogin(login).get())
                .stream()
                .map(ReceiptStats::roundTo2DecimalPlaces)
                .collect(Collectors.toList());
    }

    @GetMapping("/allReceiptLitres/{login}")
    public double getAllReceiptLitresByUserLogin(@PathVariable String login) {
        return Double.valueOf(df.format(statsService.getAllReceiptLitresByUser(userService.getUserByLogin(login).get())));
    }

    @GetMapping("/allReceiptCost/{login}")
    public double getAllReceiptCostByUserLogin(@PathVariable String login) {
        return Double.valueOf(df.format(statsService.getAllReceiptCostByUser(userService.getUserByLogin(login).get())));
    }

    @GetMapping("/counterStats/{login}")
    public List<CounterStats> getCounterStatsByUserLogin(@PathVariable String login) {
        return statsService.getCounterStatsByUser(userService.getUserByLogin(login).get())
                .stream()
                .map(CounterStats::roundTo2DecimalPlaces)
                .collect(Collectors.toList());
    }

}
