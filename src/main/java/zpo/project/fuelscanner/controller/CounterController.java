package zpo.project.fuelscanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zpo.project.fuelscanner.dto.CounterDTO;
import zpo.project.fuelscanner.model.Counter;
import zpo.project.fuelscanner.service.CounterService;
import zpo.project.fuelscanner.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/counter")
@CrossOrigin
public class CounterController {

    private CounterService counterService;
    private UserService userService;

    @Autowired
    public CounterController(CounterService counterService, UserService userService) {
        this.counterService = counterService;
        this.userService = userService;
    }

    @GetMapping("/all")
    public List<Counter> getCounters() {
        List<Counter> counterList = counterService.getCounters();
        return counterList;
    }

    @GetMapping("/{id}")
    public Counter getCounter(@PathVariable long id) {
        return counterService.getCounter(id);
    }

    @GetMapping("/allByUser/{login}")
    public List<Counter> getCountersByUser(@PathVariable String login) {
        return counterService.findAllByUser(userService.getUserByLogin(login).get());
    }

    @PostMapping
    public Counter saveCounter(@RequestBody CounterDTO counter) {
        return counterService.createCounter(counter);
    }

}
