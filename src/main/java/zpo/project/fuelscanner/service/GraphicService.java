package zpo.project.fuelscanner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import zpo.project.fuelscanner.model.Graphic;
import zpo.project.fuelscanner.repository.GraphicRepository;

import java.util.List;

@Service
public class GraphicService {
    @Autowired
    private GraphicRepository graphicRepo;

    public List<Graphic> getGraphics() {
        return graphicRepo.findAll(Sort.by(Sort.Order.desc("id")));
    }
    public Graphic getGraphic(long id) {
        return graphicRepo.getOne(id);
    }
    public Graphic createGraphic(Graphic graphic) {
        return graphicRepo.save(graphic);
    }
    public void updateGraphic(Graphic graphic) { graphic = graphicRepo.save(graphic); }
    public void deleteGraphic(long id) {
        graphicRepo.deleteById(id);
    }
}
