package g.nsu.fuel.monitoring.entities.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import g.nsu.fuel.monitoring.entities.user.Account;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.time.Instant;


@Entity
@Table(name = "refresh_token")
@Getter
@Setter
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    @Cascade(CascadeType.MERGE)
    private Account account;

    @Column(nullable = false, unique = true, name = "token")
    private String token;

    @Column(name = "finger_print", nullable = false)
    private String fingerPrint;

    @Column(nullable = false, name = "expiry_date")
    private Instant expiryDate;
}