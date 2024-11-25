package g.nsu.fuel.monitoring.services;

import g.nsu.fuel.monitoring.entities.oil.GasStation;
import g.nsu.fuel.monitoring.model.exception.NotInDataBaseException;
import g.nsu.fuel.monitoring.payload.requests.AddGasStationRequest;
import g.nsu.fuel.monitoring.payload.response.GasStationResponse;
import g.nsu.fuel.monitoring.payload.response.GasStationSummary;
import g.nsu.fuel.monitoring.repository.GasStationByAddressRepository;
import g.nsu.fuel.monitoring.repository.GasStationRepository;
import g.nsu.fuel.monitoring.repository.jdbc.GasStationJdbcRepository;
import g.nsu.fuel.monitoring.services.interfaces.GasStationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class GasStationServiceImpl implements GasStationService {

    private final GasStationJdbcRepository gasStationJdbcRepository;

    private final GasStationRepository gasStationRepository;

    private final GasStationByAddressRepository gasStationByAddressRepository;

    public List<GasStationResponse> getAllGasStations() {
        return gasStationJdbcRepository.findAllGasStations();
    }

    public void deleteGasStationById(Long id) {
        if (!gasStationRepository.existsById(id)) {
            throw new NotInDataBaseException("Заправка с айди " + id);
        }
        gasStationRepository.deleteById(id);
    }

    public GasStationSummary getGasStationSummary(Long id, Integer interval) {
        if (!gasStationRepository.existsById(id)) {
            throw new NotInDataBaseException("Заправка с айди " + id);
        }

        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(interval - 1);
        return gasStationJdbcRepository.getSummary(id, startDate, endDate);
    }

    public void addNewGasStation(AddGasStationRequest addGasStationRequest) {
        String gasStationName = addGasStationRequest.getName();
        if (!gasStationRepository.existsByName(gasStationName)) {
            throw new RuntimeException();
        }
        GasStation gasStation = new GasStation();
        gasStation.setName(gasStationName);
        gasStation.setUrl(addGasStationRequest.getSiteURL());
        gasStation.setType(addGasStationRequest.getType());
        gasStation.setEmail(addGasStationRequest.getEmail());
        gasStationRepository.save(gasStation);
    }
}
