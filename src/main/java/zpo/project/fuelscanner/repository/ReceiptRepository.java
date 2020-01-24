package zpo.project.fuelscanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zpo.project.fuelscanner.model.Receipt;
import zpo.project.fuelscanner.model.User;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
    List<Receipt> findAllByUser(User user);

    List<Receipt> findAllByUserAndReceiptLocalDateBetween(User user, LocalDate start, LocalDate end);
}
