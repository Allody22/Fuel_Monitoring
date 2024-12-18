package g.nsu.fuel.monitoring.controller;

import g.nsu.fuel.monitoring.payload.response.OilTypeResponse;
import g.nsu.fuel.monitoring.services.interfaces.OilTypesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@Tag(name = "02. Oil Controller", description = "API для получения разных типов топлива по разным фильтрам, различная работа с топливом.")
@ApiResponses(@ApiResponse(responseCode = "200", useReturnTypeSchema = true))
@RequestMapping("/api/v1/oil")
public class OilController {

    private final OilTypesService oilTypesService;

    @Operation(
            summary = "Получение всех типов топлива",
            description = """
                    Получение всех названий типов топлива, как строки.
                    """)
    @PostMapping("/types")
    public ResponseEntity<List<OilTypeResponse>> getAllOilTypeNames() {
        return ResponseEntity.ok().body(oilTypesService.getAllOilTypes());
    }

}
