package g.nsu.fuel.monitoring.services;

import g.nsu.fuel.monitoring.payload.response.GasStationResponse;
import g.nsu.fuel.monitoring.repository.jdbc.GasStationJdbcRepository;
import g.nsu.fuel.monitoring.services.interfaces.GasStationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GasStationServiceImpl implements GasStationService {

    private final GasStationJdbcRepository gasStationJdbcRepository;

    public List<GasStationResponse> getAllGasStations() {
        return gasStationJdbcRepository.findAllGasStations();
    }
}
