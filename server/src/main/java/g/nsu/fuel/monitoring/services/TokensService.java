package g.nsu.fuel.monitoring.services;

import g.nsu.fuel.monitoring.configuration.security.jwt.JwtUtils;
import g.nsu.fuel.monitoring.entities.security.RefreshToken;
import g.nsu.fuel.monitoring.entities.user.Account;
import g.nsu.fuel.monitoring.model.exception.security.FingerPrintException;
import g.nsu.fuel.monitoring.model.exception.security.InvalidTokenException;
import g.nsu.fuel.monitoring.model.exception.security.TokenExpiredException;
import g.nsu.fuel.monitoring.payload.response.JwtResponse;
import g.nsu.fuel.monitoring.payload.response.RefreshResponse;
import g.nsu.fuel.monitoring.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
public class TokensService {


    private final RefreshTokenRepository refreshTokenRepository;

    private final JwtUtils jwtUtils;

    @Value("${nsu.diploma.refreshExpirationMs}")
    private Long refreshTokenDurationMs;

    @Value("${nsu.diploma.refreshCookieName}")
    private String refreshCookieName;

    @Autowired
    public TokensService(RefreshTokenRepository refreshTokenRepository, JwtUtils jwtUtils) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtUtils = jwtUtils;
    }

    public RefreshResponse createRefreshToken(Account account, String fingerPrint) {
        RefreshToken refreshToken = new RefreshToken();

        String tokenValue = UUID.randomUUID().toString();
        while (refreshTokenRepository.existsByToken(tokenValue)) {
            tokenValue = UUID.randomUUID().toString();
        }

        refreshToken.setAccount(account);
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(tokenValue);
        refreshToken.setFingerPrint(fingerPrint);
        refreshToken = refreshTokenRepository.save(refreshToken);
        return new RefreshResponse(refreshToken.getToken(), refreshTokenDurationMs);
    }

    @Transactional(noRollbackFor = {FingerPrintException.class, InvalidTokenException.class})
    public JwtResponse createNewJwt(String refreshTokenValue, String fingerPrint) {
        if (refreshTokenValue == null || refreshTokenValue.isBlank()) {
            throw new InvalidTokenException("Refresh token is empty in createNewJwt");
        }

        RefreshToken refreshToken = refreshTokenRepository.findByToken(refreshTokenValue)
                .orElseThrow(() -> new InvalidTokenException("Refresh token not found in logout."));

        String foundedFingerPrint = refreshToken.getFingerPrint();
        if (!foundedFingerPrint.equals(fingerPrint)) {
            refreshTokenRepository.deleteByToken(refreshTokenValue);
            throw new FingerPrintException("Finger print doesn't match fingerprint in token verification");
        }

        verifyTokenExpiration(refreshToken);

        Account account = refreshToken.getAccount();

        refreshTokenRepository.deleteAllByAccount_IdAndFingerPrintAndTokenNot(account.getId(),fingerPrint, refreshToken.getToken());

        return jwtUtils.generateJwtToken(account.getPhoneNumber());
    }

    @Transactional(noRollbackFor = {FingerPrintException.class, InvalidTokenException.class})
    public void processLogout(String refreshTokenValue, String fingerPrint) {
        if (refreshTokenValue == null || refreshTokenValue.isBlank()) {
            throw new InvalidTokenException("Refresh token is empty in logout ");
        }
        RefreshToken refreshToken = refreshTokenRepository.findByToken(refreshTokenValue)
                .orElseThrow(() -> new InvalidTokenException("Refresh token not found in logout."));
        String foundedFingerPrint = refreshToken.getFingerPrint();
        if (!foundedFingerPrint.equals(fingerPrint)) {
            refreshTokenRepository.deleteByToken(refreshTokenValue);
            throw new FingerPrintException("Finger print doesn't match fingerprint in token verification");
        }

        verifyTokenExpiration(refreshToken);

        refreshTokenRepository.deleteAllByAccount_Id(refreshToken.getAccount().getId());
    }


    @Transactional
    public void verifyTokenExpiration(RefreshToken refreshToken) {
        if (refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refreshToken);
            throw new TokenExpiredException();
        }
    }
}
