package g.nsu.fuel.monitoring.entities.user;

import g.nsu.fuel.monitoring.entities.oil.GasStationByAddress;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@AllArgsConstructor
@Setter
@NoArgsConstructor
@Builder
@Table(name = "favorites")
public class Favorites {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    private Account accountId;

    @ManyToOne
    @JoinColumn(name = "gas_station_address_id", referencedColumnName = "id", nullable = false)
    private GasStationByAddress stationId;
}
