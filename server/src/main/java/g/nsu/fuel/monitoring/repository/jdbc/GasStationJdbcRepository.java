package g.nsu.fuel.monitoring.repository.jdbc;

import g.nsu.fuel.monitoring.payload.response.GasStationByAddressResponse;
import g.nsu.fuel.monitoring.payload.response.GasStationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
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
                            'id', gsa.id,
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

            List<GasStationByAddressResponse> addresses = new ArrayList<>();

            // Извлекаем JSON строку и обрабатываем вручную
            String addressesJson = rs.getString("addresses");
            if (addressesJson != null && !addressesJson.isEmpty()) {
                // Разбиваем JSON массив объектов вручную
                String[] addressEntries = addressesJson.replace("[", "").replace("]", "").split("},\\s*\\{");

                for (String addressEntry : addressEntries) {
                    GasStationByAddressResponse addressResponse = new GasStationByAddressResponse();

                    // Удаляем лишние символы и разделяем поля
                    addressEntry = addressEntry.replace("{", "").replace("}", "").replace("\"", "");
                    String[] fields = addressEntry.split(",");

                    for (String field : fields) {
                        String[] keyValue = field.split(":");
                        if (keyValue.length == 2) {
                            String key = keyValue[0].trim();
                            String value = keyValue[1].trim();

                            switch (key) {
                                case "rating":
                                    addressResponse.setRating(value.isEmpty() ? null : Double.parseDouble(value));
                                    break;
                                case "address":
                                    addressResponse.setAddress(value.isEmpty() ? null : value);
                                    break;
                                case "feedbacks":
                                    addressResponse.setFeedbacks(value.isEmpty() ? null : Integer.parseInt(value));
                                    break;
                                case "updatedAt":
                                    addressResponse.setUpdatedAt(value.isEmpty() ? null : LocalDate.parse(value));
                                    break;
                                case "id":
                                    addressResponse.setId(value.isEmpty() ? null : Long.parseLong(value));
                                    break;
                            }
                        }
                    }
                    addresses.add(addressResponse);
                }
            }

            gasStationResponse.setGasStations(addresses);
            return gasStationResponse;
        });
    }
}
