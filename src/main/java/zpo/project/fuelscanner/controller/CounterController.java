package zpo.project.fuelscanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zpo.project.fuelscanner.model.Counter;
import zpo.project.fuelscanner.service.CounterService;
import zpo.project.fuelscanner.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/counter")
@CrossOrigin
public class CounterController {

    @Autowired
    CounterService counterService;

    @Autowired
    UserService userService;

    @GetMapping("/all")
    public List<Counter> getCounters() {
        List<Counter> counterList = counterService.getCounters();
        return counterList;
    }

    @GetMapping("/{id}")
    public Counter getCounter(@PathVariable long id) {
        return counterService.getCounter(id);
    }

    @GetMapping("/allByUser/{userId}")
    public List<Counter> getCountersByUser(@PathVariable long userId) {
        return counterService.findAllByUser(userService.getUser(userId));
    }

    @PostMapping("/saveCounter")
    public Counter saveCounter(@RequestBody Counter counter) {
        return counterService.createCounter(counter);
    }

}