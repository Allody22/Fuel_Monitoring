package g.nsu.fuel.monitoring.repository;

import g.nsu.fuel.monitoring.entities.oil.GasStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GasStationRepository extends JpaRepository<GasStation, Long> {

}
