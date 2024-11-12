package g.nsu.fuel.monitoring.entities.oil;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "oil_type_price_address_history")
public class GasStationSalesHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "oil_type_id", nullable = false)
    private OilTypes oilTypes;

    @Column(name = "price")
    private Double price;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "gas_station_address_id", nullable = false)
    private GasStationByAddress gasStationByAddress;
}