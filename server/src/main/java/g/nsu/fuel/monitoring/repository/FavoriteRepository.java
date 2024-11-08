package g.nsu.fuel.monitoring.repository;

import g.nsu.fuel.monitoring.entities.oil.GasStationByAddress;
import g.nsu.fuel.monitoring.entities.user.Account;
import g.nsu.fuel.monitoring.entities.user.Favorites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorites, Long> {

    Optional<Favorites> findByAccountIdAndStationId(Account account, GasStationByAddress gasStationByAddress);
}
