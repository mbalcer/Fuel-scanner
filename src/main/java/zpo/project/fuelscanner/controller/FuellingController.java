package zpo.project.fuelscanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zpo.project.fuelscanner.model.Fuelling;
import zpo.project.fuelscanner.service.FuellingService;

import java.util.List;

@RestController
@RequestMapping("/api/fuelling")
@CrossOrigin
public class FuellingController {

    @Autowired
    FuellingService fuellingService;

    @GetMapping("/all")
    public List<Fuelling> getFuellings() {
        List<Fuelling> fuellingList = fuellingService.getFuellings();
        return fuellingList;
    }

    @GetMapping("/{id}")
    public Fuelling getFuelling(@PathVariable long id) {
        return fuellingService.getFuelling(id);
    }
}
