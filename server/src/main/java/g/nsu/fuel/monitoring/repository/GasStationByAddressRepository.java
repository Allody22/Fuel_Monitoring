package g.nsu.fuel.monitoring.repository;

import g.nsu.fuel.monitoring.entities.oil.GasStationByAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GasStationByAddressRepository extends JpaRepository<GasStationByAddress, Long> {

}
