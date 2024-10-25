package g.nsu.fuel.monitoring.services;


import g.nsu.fuel.monitoring.configuration.security.jwt.JwtUtils;
import g.nsu.fuel.monitoring.entities.user.Account;
import g.nsu.fuel.monitoring.model.exception.UserAlreadyExistException;
import g.nsu.fuel.monitoring.payload.response.AccountInfoResponse;
import g.nsu.fuel.monitoring.payload.response.JwtResponse;
import g.nsu.fuel.monitoring.payload.response.RefreshResponse;
import g.nsu.fuel.monitoring.repository.AccountRepository;
import g.nsu.fuel.monitoring.services.interfaces.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.CredentialException;


@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;

    private final TokensService tokensService;

    private final JwtUtils jwtUtils;

    @Override
    public RefreshResponse login(String username, String password, String fingerPrint) throws CredentialException {
        Account account = accountRepository.findByPhoneNumber(username)
                .orElseThrow(() -> new CredentialException("Invalid credentials"));

        if (!passwordEncoder.matches(password, account.getPassword())) {
            throw new CredentialException("Invalid credentials");
        }

        JwtResponse jwtResponse = jwtUtils.generateJwtToken(username);

        RefreshResponse refreshResponse = tokensService.createRefreshToken(account,fingerPrint);

        refreshResponse.setAccess_token(jwtResponse.getAccess_token());
        refreshResponse.setExpires_in(jwtResponse.getExpires_in());

        return refreshResponse;
    }

    @Override
    public RefreshResponse register(String username, String password, String fingerPrint) {
        if (accountRepository.existsByPhoneNumber(username)){
            throw new UserAlreadyExistException("Аккаунт с телефоном " + username);
        }
        Account account = new Account();
        account.setPassword(passwordEncoder.encode(password));
        account.setPhoneNumber(username);
        accountRepository.save(account);
        JwtResponse jwtResponse = jwtUtils.generateJwtToken(username);

        RefreshResponse refreshResponse = tokensService.createRefreshToken(account,fingerPrint);

        refreshResponse.setAccess_token(jwtResponse.getAccess_token());
        refreshResponse.setExpires_in(jwtResponse.getExpires_in());

        return refreshResponse;
    }

    @Override
    public AccountInfoResponse getAccountInfo(UserDetails userDetails) {
        return null;
    }
}
