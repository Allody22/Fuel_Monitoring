package g.nsu.fuel.monitoring.controller;

import g.nsu.fuel.monitoring.payload.response.DataResponse;
import g.nsu.fuel.monitoring.payload.response.GasStationByAddressResponse;
import g.nsu.fuel.monitoring.services.UserDetailsImpl;
import g.nsu.fuel.monitoring.services.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.CredentialException;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@Tag(name = "04. User Controller", description = "API для получения работы с профилем юзера.")
@RequestMapping("/api/v1/user")
@ApiResponses(@ApiResponse(responseCode = "200", useReturnTypeSchema = true))
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Добавление нового адреса АЗС определённой сети в избранное.",
            description = """
                    Получается айди АЗС для определённого адреса и оно добавляется в набор избранного.
                    """)
    @PostMapping("/favorites/{stationId}")
    public ResponseEntity<DataResponse> addFavorites(@AuthenticationPrincipal UserDetails user, @PathVariable Long stationId) throws CredentialException {
        userService.addFavorite(user, stationId);
        return ResponseEntity.ok().body(new DataResponse(true));
    }

    @Operation(
            summary = "Удаление адреса АЗС определённой сети из избранного.",
            description = """
                    Получается айди АЗС для определённого адреса и оно добавляется .
                    """)
    @DeleteMapping("/favorites/{stationId}")
    public ResponseEntity<DataResponse> deleteFavorites(@AuthenticationPrincipal UserDetails user, @PathVariable Long stationId) throws CredentialException {
        userService.deleteFavorite(user, stationId);
        return ResponseEntity.ok().body(new DataResponse(true));
    }


    @Operation(
            summary = "Получение всего списка избранных АЗС пользователя.",
            description = """
                    """)
    @GetMapping("/favorites")
    public ResponseEntity<List<GasStationByAddressResponse>> getAllFavorites(@AuthenticationPrincipal UserDetails user) throws CredentialException {
        return ResponseEntity.ok(userService.getAllFavoritesByUser((UserDetailsImpl) user));
    }
}
