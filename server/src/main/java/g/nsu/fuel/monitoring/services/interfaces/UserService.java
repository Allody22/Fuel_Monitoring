package g.nsu.fuel.monitoring.services.interfaces;

import g.nsu.fuel.monitoring.payload.response.GasStationByAddressResponse;
import org.springframework.security.core.userdetails.UserDetails;

import javax.security.auth.login.CredentialException;
import java.util.List;

public interface UserService {

    void addFavorite(UserDetails userDetails, Long favoriteId) throws CredentialException;

    void deleteFavorite(UserDetails userDetails, Long favoriteId) throws CredentialException;

    List<GasStationByAddressResponse> getAllFavoritesByUser(UserDetails userDetails) throws CredentialException;
    }
