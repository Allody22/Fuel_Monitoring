package g.nsu.fuel.monitoring.services.interfaces;

import g.nsu.fuel.monitoring.payload.response.AccountInfoResponse;
import g.nsu.fuel.monitoring.payload.response.JwtResponse;
import g.nsu.fuel.monitoring.services.UserDetailsImpl;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.http.ResponseCookie;

import javax.security.auth.login.CredentialException;

/**
 * Интерфейс AuthService предоставляет методы для аутентификации пользователей, регистрации и получения информации об аккаунте.
 */
public interface AuthService {

    /**
     * Аутентификация пользователя с предоставленными учетными данными.
     * Будет проверка переданных данных и сверка пароля.
     *
     * @param username телефон пользователя
     * @param password пароль пользователя
     * @return объект response, содержащий JWT и Refresh токен, созданный по данным пользователя.
     */
    Pair<JwtResponse, ResponseCookie> login(String username, String password, String fingerPrint) throws CredentialException;

    /**
     * Регистрация нового пользователя в системе.
     *
     * @param username телефон пользователя
     * @param password пароль пользователя
     * @return объект response, содержащий JWT и Refresh токен, созданный по данным пользователя.
     */
    Pair<JwtResponse, ResponseCookie> register(String username, String password, String fingerprint);

    /**
     * Получение информации о текущем пользователе на основе его токена.
     *
     * @param userDetails объект UserDetails из которого будет получен username при правильном JWT.
     * @return объект AccountInfoResponse с основной информацией об аккаунте пользователя.
     */
    AccountInfoResponse getAccountInfo(UserDetailsImpl userDetails) throws CredentialException;
}
