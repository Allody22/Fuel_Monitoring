package g.nsu.fuel.monitoring.repository;

import g.nsu.fuel.monitoring.entities.oil.GasStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GasStationRepository extends JpaRepository<GasStation, Long> {

    boolean existsByName(String name);

    Optional<GasStation> findByName(String name);
}
