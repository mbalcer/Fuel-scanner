package zpo.project.fuelscanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zpo.project.fuelscanner.model.ReceiptStats;
import zpo.project.fuelscanner.service.StatsService;
import zpo.project.fuelscanner.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/stats")
@CrossOrigin
public class StatsController {

    private StatsService statsService;
    private UserService userService;

    @Autowired
    public StatsController(StatsService statsService, UserService userService) {
        this.statsService = statsService;
        this.userService = userService;
    }

    @GetMapping("/receiptStats/{login}")
    public List<ReceiptStats> getReceiptStatsByUserLogin(@PathVariable String login) {
        return statsService.getReceiptStatsyByUserGroupedByYearMonth(userService.getUserByLogin(login));
    }

    @GetMapping("/allReceiptLitres/{login}")
    public double getAllReceiptLitresByUserLogin(@PathVariable String login) {
        return statsService.getAllReceiptLitresByUser(userService.getUserByLogin(login));
    }

    @GetMapping("/allReceiptCost/{login}")
    public double getAllReceiptCostByUserLogin(@PathVariable String login) {
        return statsService.getAllReceiptCostByUser(userService.getUserByLogin(login));
    }

}
