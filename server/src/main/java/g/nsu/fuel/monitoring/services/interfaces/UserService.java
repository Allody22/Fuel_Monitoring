package g.nsu.fuel.monitoring.services.interfaces;

import g.nsu.fuel.monitoring.payload.response.JwtResponse;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

    JwtResponse changeAccountInfo(UserDetails userDetails);

}
