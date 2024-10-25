package g.nsu.fuel.monitoring.repository;

import g.nsu.fuel.monitoring.entities.security.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    boolean existsByToken(String token);

    Optional<RefreshToken> findByToken(String token);

    void deleteByToken(String token);

    void deleteAllByAccount_IdAndFingerPrint(Long accountId, String fingerPrint);

    void deleteAllByAccount_Id(Long accountId);

    void deleteAllByAccount_IdAndFingerPrintAndTokenNot(Long accountId, String fingerPrint, String token);
}
