package g.nsu.fuel.monitoring.repository;

import g.nsu.fuel.monitoring.entities.user.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    boolean existsByPhoneNumber(String phoneNumber);

    Optional<Account> findByPhoneNumber(String phoneNumber);
}
