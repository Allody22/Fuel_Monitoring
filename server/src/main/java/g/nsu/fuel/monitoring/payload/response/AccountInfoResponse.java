package g.nsu.fuel.monitoring.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountInfoResponse {

    private Long id;

    private String phoneNumber;

    private String oilType;

    private List<String> roles;
}
