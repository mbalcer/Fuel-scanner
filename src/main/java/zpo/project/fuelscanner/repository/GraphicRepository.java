package zpo.project.fuelscanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zpo.project.fuelscanner.model.Graphic;

@Repository
public interface GraphicRepository extends JpaRepository<Graphic, Long> {
}
