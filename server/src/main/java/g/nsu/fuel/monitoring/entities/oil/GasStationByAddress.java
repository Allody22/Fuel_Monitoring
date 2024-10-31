package g.nsu.fuel.monitoring.entities.oil;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "gas_station_address")
public class GasStationByAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "address")
    private String address;

    @Column(name = "feedbacks")
    private Integer feedbacks;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @ManyToOne
    @JoinColumn(name = "gas_station_id", nullable = false)
    private GasStation gasStation;

    @OneToMany(mappedBy = "gasStationByAddress", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GasStationSalesHistory> salesHistories;

}