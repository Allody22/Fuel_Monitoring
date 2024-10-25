package g.nsu.fuel.monitoring.entities.oil;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "oil_types")
@AllArgsConstructor
@NoArgsConstructor
public class OilTypes {

    @Id
    @Column(name = "type", nullable = false)
    private String oilType;


}