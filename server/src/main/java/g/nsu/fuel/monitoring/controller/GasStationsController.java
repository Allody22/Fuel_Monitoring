package g.nsu.fuel.monitoring.controller;

import g.nsu.fuel.monitoring.payload.response.GasStationResponse;
import g.nsu.fuel.monitoring.services.interfaces.GasStationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@Tag(name = "03. Gas Station Controller", description = "API для получения АЗС, получение сводки по ним, их адресов..")
@ApiResponses(@ApiResponse(responseCode = "200", useReturnTypeSchema = true))
@RequestMapping("/api/v1/stations")
public class GasStationsController {

    private final GasStationService gasStationService;

    @Operation(
            summary = "Получение информации о сетях заправок в нашей БД",
            description = """
                    Получение всех компаний АЗС.
                    """)
    @GetMapping("/all")
    public ResponseEntity<List<GasStationResponse>> getAllGasStation() {
        return ResponseEntity.ok().body(gasStationService.getAllGasStations());
    }
}
