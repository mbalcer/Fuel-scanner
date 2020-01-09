package zpo.project.fuelscanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import zpo.project.fuelscanner.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.login = :log AND u.password = :pwd")
    Optional<User> findUser(
            @Param("log") String login,
            @Param("pwd") String password);

    Optional<User> findByLogin(String login);
}