package g.nsu.fuel.monitoring.services.interfaces;

import g.nsu.fuel.monitoring.payload.response.AccountInfoResponse;
import g.nsu.fuel.monitoring.payload.response.JwtResponse;
import org.springframework.security.core.userdetails.UserDetails;

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
     * @return объект JwtResponse, содержащий JWT, созданный по данным пользователя.
     */
    JwtResponse login(String username, String password);

    /**
     * Регистрация нового пользователя в системе.
     *
     * @param username телефон пользователя
     * @param password пароль пользователя
     * @return объект JwtResponse, содержащий JWT, созданный по данным пользователя.
     */
    JwtResponse register(String username, String password);

    /**
     * Получение информации о текущем пользователе на основе его токена.
     *
     * @param userDetails объект UserDetails из которого будет получен username при правильном JWT.
     * @return объект AccountInfoResponse с основной информацией об аккаунте пользователя.
     */
    AccountInfoResponse getAccountInfo(UserDetails userDetails);
}
