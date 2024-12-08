package g.nsu.fuel.monitoring.services;


import g.nsu.fuel.monitoring.configuration.security.jwt.JwtUtils;
import g.nsu.fuel.monitoring.entities.user.Account;
import g.nsu.fuel.monitoring.model.exception.UserAlreadyExistException;
import g.nsu.fuel.monitoring.payload.response.AccountInfoResponse;
import g.nsu.fuel.monitoring.payload.response.JwtResponse;
import g.nsu.fuel.monitoring.repository.AccountRepository;
import g.nsu.fuel.monitoring.services.interfaces.AuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.CredentialException;


@Slf4j
@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;

    private final TokensService tokensService;

    private final JwtUtils jwtUtils;

    @Override
    @Transactional
    public Pair<JwtResponse, ResponseCookie> login(String username, String password, String fingerPrint) throws CredentialException {
        if (username == null || username.isBlank()) {
            throw new CredentialException("Invalid credentials");
        }

        Account account = accountRepository.findByPhoneNumber(username)
                .orElseThrow(() -> new CredentialException("Invalid credentials"));


        if (!passwordEncoder.matches(password, account.getPassword())) {
            throw new CredentialException("Invalid credentials");
        }
        tokensService.deleteAllAccountTokens(account);

        ResponseCookie refreshCookie = tokensService.wrapRefreshTokenWithCookie(account, fingerPrint);

        return new Pair<>(jwtUtils.generateJwtToken(username), refreshCookie);

    }

    @Override
    public Pair<JwtResponse, ResponseCookie> register(String username, String password, String fingerPrint) {
        if (accountRepository.existsByPhoneNumber(username)){
            throw new UserAlreadyExistException("Аккаунт с телефоном " + username);
        }
        Account account = new Account();
        account.setPassword(passwordEncoder.encode(password));
        account.setPhoneNumber(username);
        accountRepository.save(account);
        tokensService.deleteAllAccountTokens(account);
        ResponseCookie refreshCookie = tokensService.wrapRefreshTokenWithCookie(account, fingerPrint);

        return new Pair<>(jwtUtils.generateJwtToken(username), refreshCookie);

    }

    @Override
    public AccountInfoResponse getAccountInfo(UserDetails userDetails) {
        return null;
    }
}
