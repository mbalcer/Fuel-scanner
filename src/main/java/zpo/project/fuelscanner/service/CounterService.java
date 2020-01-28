package zpo.project.fuelscanner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import zpo.project.fuelscanner.model.Counter;
import zpo.project.fuelscanner.model.User;
import zpo.project.fuelscanner.repository.CounterRepository;

import java.util.List;

@Service
public class CounterService {

    @Autowired
    private CounterRepository counterRepository;

    public List<Counter> getCounters() {
        return counterRepository.findAll(Sort.by(Sort.Order.desc("id")));
    }

    public Counter getCounter(long id) {
        return counterRepository.findById(id).get();
    }

    public Counter createCounter(Counter counter) {
        return counterRepository.save(counter);
    }

    public void updateCounter(Counter counter) {
        counterRepository.save(counter);
    }

    public void deleteCounter(long id) {
        counterRepository.deleteById(id);
    }

    public  List<Counter> findAllByUser(User user){
        return counterRepository.findAllByUser(user);
    }

}