package zpo.project.fuelscanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zpo.project.fuelscanner.model.Fuelling;
import zpo.project.fuelscanner.model.User;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FuellingRepository extends JpaRepository<Fuelling, Long> {
    List<Fuelling> findAllByUser(User user);

    List<Fuelling> findAllByUserAndFuellingLocalDateBetween(User user, LocalDate start, LocalDate end);
}
