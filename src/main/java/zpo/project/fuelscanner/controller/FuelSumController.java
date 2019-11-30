package zpo.project.fuelscanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import zpo.project.fuelscanner.model.FuelSum;
import zpo.project.fuelscanner.service.FuelSumService;

import java.util.List;

@RestController
public class FuelSumController {
    @Autowired
    FuelSumService fuelSumService;

    @GetMapping("/fuel")
    public List<FuelSum> getFuels(){

    List<FuelSum> fuelSums = fuelSumService.getFuelSums();
    return fuelSums;
    }
}
