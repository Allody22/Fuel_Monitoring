package g.nsu.fuel.monitoring.services;

import g.nsu.fuel.monitoring.payload.response.GasStationResponse;
import g.nsu.fuel.monitoring.payload.response.OilTypeResponse;
import g.nsu.fuel.monitoring.repository.jdbc.GasStationJdbcRepository;
import g.nsu.fuel.monitoring.repository.jdbc.OilTypesJdbcRepository;
import g.nsu.fuel.monitoring.services.interfaces.OilTypesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OilTypesServiceImpl implements OilTypesService {

    private final OilTypesJdbcRepository oilTypesJdbcRepository;

    private final GasStationJdbcRepository gasStationJdbcRepository;

    @Override
    public List<OilTypeResponse> getAllOilTypes() {
        return oilTypesJdbcRepository.getAllOilTypes();
    }

    public List<GasStationResponse> getAllGasStations() {
        return gasStationJdbcRepository.findAllGasStations();
    }
}
