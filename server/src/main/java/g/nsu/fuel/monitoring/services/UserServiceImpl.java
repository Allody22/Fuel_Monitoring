package g.nsu.fuel.monitoring.services;


import g.nsu.fuel.monitoring.entities.oil.GasStationByAddress;
import g.nsu.fuel.monitoring.entities.user.Account;
import g.nsu.fuel.monitoring.entities.user.Favorites;
import g.nsu.fuel.monitoring.model.exception.NotInDataBaseException;
import g.nsu.fuel.monitoring.repository.AccountRepository;
import g.nsu.fuel.monitoring.repository.FavoriteRepository;
import g.nsu.fuel.monitoring.repository.GasStationByAddressRepository;
import g.nsu.fuel.monitoring.services.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.security.auth.login.CredentialException;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final AccountRepository accountRepository;

    private final FavoriteRepository favoriteRepository;

    private final GasStationByAddressRepository gasStationByAddressRepository;

    @Override
    public void addFavorite(UserDetails userDetails, Long favoriteId) throws CredentialException {
        String userName = userDetails.getUsername();
        if (userName == null || userName.isBlank()) {
            throw new CredentialException("Invalid credentials");
        }

        Account account = accountRepository.findByPhoneNumber(userName)
                .orElseThrow(() -> new CredentialException("Invalid credentials"));

        GasStationByAddress gasStationByAddress = gasStationByAddressRepository.findById(favoriteId)
                .orElseThrow(() -> new NotInDataBaseException("Заправка с айди " + favoriteId));

        Favorites favorites = favoriteRepository.findByAccountIdAndStationId(account, gasStationByAddress)
                .orElseThrow(() -> new NotInDataBaseException("Избранное с айди " + favoriteId));

        account.deleteFavorite(favorites);
        favoriteRepository.delete(favorites);
    }


    @Override
    public void deleteFavorite(UserDetails userDetails, Long favoriteId) throws CredentialException {
        String userName = userDetails.getUsername();
        if (userName == null || userName.isBlank()) {
            throw new CredentialException("Invalid credentials");
        }

        Account account = accountRepository.findByPhoneNumber(userName)
                .orElseThrow(() -> new CredentialException("Invalid credentials"));

        if (!gasStationByAddressRepository.existsById(favoriteId)){
            throw new NotInDataBaseException("Заправка с айди " + favoriteId);
        }
        Favorites favorites = favoriteRepository.findById(favoriteId)
                .orElseThrow(() -> new NotInDataBaseException("Заправка с айди " + favoriteId));

        favoriteRepository.delete(favorites);

        account.addFavorite(favorites);
    }
}
