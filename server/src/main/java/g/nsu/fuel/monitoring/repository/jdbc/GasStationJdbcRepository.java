package g.nsu.fuel.monitoring.repository.jdbc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import g.nsu.fuel.monitoring.payload.response.GasStationByAddressResponse;
import g.nsu.fuel.monitoring.payload.response.GasStationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Slf4j
public class GasStationJdbcRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public GasStationJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
    }

    public List<GasStationResponse> findAllGasStations() {
        MapSqlParameterSource params = new MapSqlParameterSource();
        String sql = """
                SELECT
                    gs.id AS gas_station_id,
                    gs.name AS gas_station_name,
                    gs.url AS gas_station_url,
                    gs.type AS gas_station_type,
                    gs.email AS gas_station_email,
                    JSON_AGG(
                        JSON_BUILD_OBJECT(
                            'rating', gsa.rating,
                            'address', gsa.address,
                            'feedbacks', gsa.feedbacks,
                            'updatedAt', gsa.updated_at
                        )
                    ) AS addresses
                FROM
                    gas_station gs
                LEFT JOIN
                    gas_station_address gsa ON gs.id = gsa.gas_station_id
                GROUP BY
                    gs.id, gs.name, gs.url, gs.type, gs.email;
                """;
        return namedParameterJdbcTemplate.query(sql, params, (rs, rowNum) -> {
            GasStationResponse gasStationResponse = new GasStationResponse();
            gasStationResponse.setName(rs.getString("gas_station_name"));
            gasStationResponse.setSiteURL(rs.getString("gas_station_url"));
            gasStationResponse.setEmail(rs.getString("gas_station_email"));
            gasStationResponse.setType(rs.getString("gas_station_type"));

            ObjectMapper objectMapper = new ObjectMapper();
            String addressesJson = rs.getString("addresses");
            List<GasStationByAddressResponse> addresses = null;
            try {
                addresses = objectMapper.readValue(
                        addressesJson, new TypeReference<>() {
                        }
                );
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            gasStationResponse.setGasStations(addresses);

            return gasStationResponse;
        });
    }
}
