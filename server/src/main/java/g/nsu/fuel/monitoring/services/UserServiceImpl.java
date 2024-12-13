package g.nsu.fuel.monitoring.services;


import g.nsu.fuel.monitoring.entities.oil.GasStationByAddress;
import g.nsu.fuel.monitoring.entities.user.Account;
import g.nsu.fuel.monitoring.entities.user.Favorites;
import g.nsu.fuel.monitoring.model.exception.NotInDataBaseException;
import g.nsu.fuel.monitoring.payload.response.GasStationByAddressResponse;
import g.nsu.fuel.monitoring.repository.AccountRepository;
import g.nsu.fuel.monitoring.repository.FavoriteRepository;
import g.nsu.fuel.monitoring.repository.GasStationByAddressRepository;
import g.nsu.fuel.monitoring.services.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.CredentialException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final AccountRepository accountRepository;

    private final FavoriteRepository favoriteRepository;

    private final GasStationByAddressRepository gasStationByAddressRepository;

    @Override
    @Transactional
    public void addFavorite(UserDetails userDetails, Long favoriteId) throws CredentialException {
        Account account = accountRepository.findById(((UserDetailsImpl) (userDetails)).getId())
                .orElseThrow(() -> new CredentialException("Invalid credentials"));

        GasStationByAddress gasStationByAddress = gasStationByAddressRepository.findById(favoriteId)
                .orElseThrow(() -> new NotInDataBaseException("Заправка с айди " + favoriteId));

        Favorites favorites = new Favorites();
        favorites.setAccountId(account);
        favorites.setStationId(gasStationByAddress);
        favoriteRepository.save(favorites);
    }


    @Override
    @Transactional
    public void deleteFavorite(UserDetails userDetails, Long favoriteId) throws CredentialException {
        Account account = accountRepository.findById(((UserDetailsImpl) (userDetails)).getId())
                .orElseThrow(() -> new CredentialException("Invalid credentials"));

        GasStationByAddress gasStationByAddress = gasStationByAddressRepository.findById(favoriteId)
                .orElseThrow(()-> new NotInDataBaseException("Заправка с айди " + favoriteId));

        List<Favorites> favoritesList = favoriteRepository.findAllByAccountIdAndStationId(account, gasStationByAddress);

        if (favoritesList.isEmpty()) {
            throw new NotInDataBaseException("Ваша избранная заправка с данными условиями не найдена");
        }

        favoriteRepository.deleteAll(favoritesList);
    }

    @Override
    public List<GasStationByAddressResponse> getAllFavoritesByUser(UserDetails userDetails) throws CredentialException {
        Account account = accountRepository.findById(((UserDetailsImpl) (userDetails)).getId())
                .orElseThrow(() -> new CredentialException("Invalid credentials"));

        Set<Favorites> userFavorites = account.getFavorites();
        List<GasStationByAddressResponse> gasStationByAddressResponses = new ArrayList<>();
        for (Favorites favorites : userFavorites) {
            gasStationByAddressResponses.add(getResponse(favorites.getStationId()));
        }

        return gasStationByAddressResponses;
    }

    private GasStationByAddressResponse getResponse(GasStationByAddress gasStationByAddress) {
        return GasStationByAddressResponse.builder()
                .id(gasStationByAddress.getId())
                .address(gasStationByAddress.getAddress())
                .rating(gasStationByAddress.getRating())
                .feedbacks(gasStationByAddress.getFeedbacks())
                .updatedAt(gasStationByAddress.getUpdatedAt())
                .build();
    }
}
