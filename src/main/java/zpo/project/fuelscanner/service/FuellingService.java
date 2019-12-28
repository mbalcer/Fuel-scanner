package zpo.project.fuelscanner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import zpo.project.fuelscanner.model.Fuelling;
import zpo.project.fuelscanner.repository.FuellingRepository;

import java.util.List;

@Service
public class FuellingService {

    @Autowired
    FuellingRepository fuellingRepository;

    public List<Fuelling> getFuellings() {
        return fuellingRepository.findAll(Sort.by(Sort.Order.desc("id")));
    }

    public Fuelling getFuelling(long id) {
        return fuellingRepository.findById(id).get();
    }

    public Fuelling createFuelling(Fuelling fuelling) {
        return fuellingRepository.save(fuelling);
    }

    public void updateFuelling(Fuelling fuelling) {
        fuellingRepository.save(fuelling);
    }

    public void deleteFuelling(long id) {
        fuellingRepository.deleteById(id);
    }
}
