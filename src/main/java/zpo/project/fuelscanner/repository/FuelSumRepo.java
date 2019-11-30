package zpo.project.fuelscanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zpo.project.fuelscanner.model.FuelSum;

@Repository
public interface FuelSumRepo extends JpaRepository<FuelSum, Long> {

}