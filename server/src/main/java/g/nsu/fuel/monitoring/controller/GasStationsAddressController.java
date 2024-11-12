package g.nsu.fuel.monitoring.controller;

import g.nsu.fuel.monitoring.payload.response.GasStationAddressSummary;
import g.nsu.fuel.monitoring.services.interfaces.GasStationAddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
@Tag(name = "05. Gas Station by Address Controller", description = "API для работы с конкретными филиалами АЗС")
@ApiResponses(@ApiResponse(responseCode = "200", useReturnTypeSchema = true))
@RequestMapping("/api/v1/stations/address")
public class GasStationsAddressController {

    private final GasStationAddressService gasStationAddressService;

    @Operation(
            summary = "Получение  о сетях заправок в нашей БД",
            description = """
                    Получение всех компаний АЗС.
                    """)
    @GetMapping("/summary/{id}")
    public ResponseEntity<GasStationAddressSummary> getGasStationSummary(@PathVariable Long id, @RequestParam(defaultValue = "30") int interval) {
        return ResponseEntity.ok().body(gasStationAddressService.getGasStationAddressSummary(id, interval));
    }
}
