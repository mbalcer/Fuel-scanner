package zpo.project.fuelscanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zpo.project.fuelscanner.model.FuelSum;
import zpo.project.fuelscanner.service.FuelSumService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/fuel")
@CrossOrigin
public class FuelSumController {

    private FuelSumService fuelSumService;

    @Autowired
    public FuelSumController(FuelSumService fuelSumService) {
        this.fuelSumService = fuelSumService;
    }

    @GetMapping("/all")
    public List<FuelSum> getFuels() {
        List<FuelSum> fuelSums = fuelSumService.getFuelSums();
        return fuelSums;
    }

    @GetMapping("/last")
    public FuelSum getLastFuel() {
        return fuelSumService.getFuelSums().stream().limit(1).collect(Collectors.toList()).get(0);
    }
}
