package g.nsu.fuel.monitoring.controller;

import g.nsu.fuel.monitoring.payload.requests.LoginRequest;
import g.nsu.fuel.monitoring.payload.requests.RefreshFingerprintRequest;
import g.nsu.fuel.monitoring.payload.requests.RegistrationRequest;
import g.nsu.fuel.monitoring.payload.response.DataResponse;
import g.nsu.fuel.monitoring.payload.response.ErrorResponse;
import g.nsu.fuel.monitoring.payload.response.JwtResponse;
import g.nsu.fuel.monitoring.services.TokensService;
import g.nsu.fuel.monitoring.services.interfaces.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.CredentialException;

@Slf4j
@RestController
@AllArgsConstructor
@Tag(name = "00. Auth Controller", description = "API для работы с авторизацией, регистрацией и токенами.")
@ApiResponses(@ApiResponse(responseCode = "200", useReturnTypeSchema = true))
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final TokensService tokensService;

    private final AuthService authService;

    @Operation(
            summary = "Выход из аккаунта",
            description = """
                    Выход из текущей сессии аккаунта с удаление рефреш-токенов, связанных с данной сессией и фингерпринтом.
                    Фингерпринт проверяется для обеспечения безопасности.
                    Если рефреш-токен был кем-то перехвачен, токен будет удален, и пользователь не получит никакой дополнительной информации.
                    """)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Все рефреш-токены аккаунта, связанные с этим фингерпринтом (сессией), удалены из базы данных", content = @Content(schema = @Schema(implementation = DataResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос или данные (например, неверный фингерпринт или отсутствует рефреш-токен)", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)
    })
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody RefreshFingerprintRequest request) {
        tokensService.processLogout(request.getRefreshToken(), request.getFingerprint());
        return ResponseEntity.ok().body(new DataResponse(true));
    }


    @Operation(
            summary = "Обновление JWT токена без создания нового рефреш-токена.",
            description = """
                    Обновляет JWT токен без создания нового рефреш-токена.
                    Запрос ожидает рефреш-токен и фингерпринт через тело запроса.
                    Проверяется, что рефреш-токен принадлежит текущей сессии и фингерпринт не изменился.
                    Если все успешно, удаляются все предыдущие сессии с этим фингерпринтом, кроме текущей.
                    """)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Новый JWT токен успешно создан", content = {@Content(schema = @Schema(implementation = JwtResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос или данные", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Рефреш-токен истек или недействителен", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)
    })
    @PostMapping("/refresh/jwt")
    public ResponseEntity<?> refreshTokenPost(@RequestBody @Valid RefreshFingerprintRequest request) {
        JwtResponse jwtResponse = tokensService.createNewJwt(request.getRefreshToken(), request.getFingerprint());

        return ResponseEntity.ok()
                .body(jwtResponse);
    }

    @Operation(
            summary = "Метод для регистрации.",
            description = """
                    Сейчас в тестовых целях сразу создаётся пользователь.
                    Без проверки и подтверждения номера телефона.
                    """)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Пользователь успешно добавлен в БД", content = {@Content(schema = @Schema(implementation = DataResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)
    })
    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody @Valid RegistrationRequest request) {
        var jwtAndRefresh = authService.register(request.getPhoneNumber(), request.getPassword(), request.getFingerprint());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtAndRefresh.b.toString())
                .body(jwtAndRefresh.a);
    }

    @Operation(
            summary = "Вход в аккаунт",
            description = """
                    Позволяет пользователю войти в свой аккаунт, используя логин и пароль.
                    В случае успешной аутентификации, создаются и возвращаются JWT токены.
                    """)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешный вход в аккаунт", content = {@Content(schema = @Schema(implementation = JwtResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос или данные", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Неверный логин или пароль", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest) throws CredentialException {
        var pair = authService.login(loginRequest.getPhoneNumber(), loginRequest.getPassword(), loginRequest.getFingerprint());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, pair.b.toString())
                .body(pair.a);
    }
}
