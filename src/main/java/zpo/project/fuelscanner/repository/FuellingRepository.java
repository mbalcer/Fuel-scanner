package zpo.project.fuelscanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zpo.project.fuelscanner.model.Fuelling;

@Repository
public interface FuellingRepository extends JpaRepository<Fuelling, Long> {
}
