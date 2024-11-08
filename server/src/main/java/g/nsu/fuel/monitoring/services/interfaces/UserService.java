package g.nsu.fuel.monitoring.services.interfaces;

import g.nsu.fuel.monitoring.payload.response.JwtResponse;
import org.springframework.security.core.userdetails.UserDetails;

import javax.security.auth.login.CredentialException;

public interface UserService {

    void addFavorite(UserDetails userDetails, Long favoriteId) throws CredentialException;

    void deleteFavorite(UserDetails userDetails, Long favoriteId) throws CredentialException;

}
