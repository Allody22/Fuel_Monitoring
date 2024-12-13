package g.nsu.fuel.monitoring.entities.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import g.nsu.fuel.monitoring.entities.security.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;


@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "phone_number")
    private String phoneNumber;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Column(name = "oil_type")
    private String oilType;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "accountId")
    private Set<Favorites> favorites = new HashSet<>();

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "account_roles",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id"))
    private Set<Role> roles = new LinkedHashSet<>();

    public void addFavorite(Favorites favorite) {
        favorites.add(favorite);
    }

    public void deleteFavorite(Favorites favorite) {
        favorites.remove(favorite);
    }
}