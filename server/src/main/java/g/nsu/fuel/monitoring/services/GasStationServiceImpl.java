package g.nsu.fuel.monitoring.services;

import g.nsu.fuel.monitoring.entities.oil.GasStation;
import g.nsu.fuel.monitoring.entities.oil.GasStationByAddress;
import g.nsu.fuel.monitoring.model.exception.NotInDataBaseException;
import g.nsu.fuel.monitoring.payload.requests.AddGasStationAddressRequest;
import g.nsu.fuel.monitoring.payload.requests.AddGasStationRequest;
import g.nsu.fuel.monitoring.payload.response.GasStationResponse;
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

    public void deleteGasStationAddressById(Long id) {
        if (!gasStationByAddressRepository.existsById(id)) {
            throw new NotInDataBaseException("Заправка по адресу с айди " + id);
        }
        gasStationByAddressRepository.deleteById(id);
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

    public void addNewGasStationAddress(AddGasStationAddressRequest addGasStationAddressRequest) {
        String gasStationName = addGasStationAddressRequest.getName();
        GasStation gasStation = gasStationRepository.findByName(gasStationName)
                .orElseThrow(() -> new NotInDataBaseException("Заправка с именем " + gasStationName));
        GasStationByAddress gasStationByAddress = new GasStationByAddress();
        gasStationByAddress.setGasStation(gasStation);
        gasStationByAddress.setAddress(addGasStationAddressRequest.getAddress());
        gasStationByAddress.setFeedbacks(addGasStationAddressRequest.getFeedbacks());
        gasStationByAddress.setRating(addGasStationAddressRequest.getRating());
        gasStationByAddress.setUpdatedAt(LocalDate.now());
        gasStationByAddressRepository.save(gasStationByAddress);
    }

}
