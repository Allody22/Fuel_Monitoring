package g.nsu.fuel.monitoring.entities.oil;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "gas_station")
public class GasStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "url")
    private String url;

    @Column(name = "type")
    private String type;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "gasStation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GasStationByAddress> addresses;

}