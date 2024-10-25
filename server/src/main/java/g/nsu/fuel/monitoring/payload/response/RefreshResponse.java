package g.nsu.fuel.monitoring.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshResponse extends JwtResponse {

    private String refresh_token;

    private Long refresh_expires_in;

}
