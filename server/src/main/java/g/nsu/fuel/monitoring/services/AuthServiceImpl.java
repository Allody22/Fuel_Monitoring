package g.nsu.fuel.monitoring.services;


import g.nsu.fuel.monitoring.configuration.security.jwt.JwtUtils;
import g.nsu.fuel.monitoring.payload.response.AccountInfoResponse;
import g.nsu.fuel.monitoring.payload.response.JwtResponse;
import g.nsu.fuel.monitoring.repository.AccountRepository;
import g.nsu.fuel.monitoring.services.interfaces.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;

    private final TokensService tokensService;

    private final JwtUtils jwtUtils;

    @Override
    public JwtResponse login(String username, String password) {
        return null;
    }

    @Override
    public JwtResponse register(String username, String password) {
        return null;
    }

    @Override
    public AccountInfoResponse getAccountInfo(UserDetails userDetails) {
        return null;
    }
}
