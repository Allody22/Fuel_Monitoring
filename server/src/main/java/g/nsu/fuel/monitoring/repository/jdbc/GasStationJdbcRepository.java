package g.nsu.fuel.monitoring.repository.jdbc;

import g.nsu.fuel.monitoring.payload.response.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
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

    public GasStationSummary getSummary(Long stationId, LocalDate startDate, LocalDate endDate) {
        String dataSql = """
                WITH date_series AS (
                    SELECT generate_series(:startDate::date, :endDate::date, interval '1 day') AS date
                ),
                oil_types_list AS (
                    SELECT DISTINCT ot.type
                    FROM oil_types ot
                    JOIN oil_type_price_address_history p ON p.oil_type_id = ot.type
                    JOIN gas_station_address ga ON ga.id = p.gas_station_address_id
                    WHERE ga.gas_station_id = :stationId
                      AND p.date BETWEEN :startDate AND :endDate
                ),
                daily_prices AS (
                    SELECT
                        p.date,
                        p.oil_type_id AS type,
                        AVG(p.price) AS average_price
                    FROM
                        oil_type_price_address_history p
                    JOIN
                        gas_station_address ga ON ga.id = p.gas_station_address_id
                    WHERE
                        ga.gas_station_id = :stationId
                        AND p.date BETWEEN :startDate AND :endDate
                    GROUP BY
                        p.date, p.oil_type_id
                )
                SELECT
                    d.date,
                    otl.type,
                    dp.average_price AS price
                FROM
                    date_series d
                CROSS JOIN
                    oil_types_list otl
                LEFT JOIN
                    daily_prices dp ON dp.date = d.date AND dp.type = otl.type
                ORDER BY
                    d.date, otl.type
                """;

        String avgSql = """
                SELECT
                    ot.type AS type,
                    AVG(p.price) AS average_price
                FROM
                    oil_type_price_address_history p
                JOIN
                    oil_types ot ON p.oil_type_id = ot.type
                JOIN
                    gas_station_address ga ON ga.id = p.gas_station_address_id
                WHERE
                    ga.gas_station_id = :stationId
                    AND p.date BETWEEN :startDate AND :endDate
                GROUP BY
                    ot.type
                """;

        String countAddressesSql = """
                SELECT COUNT(DISTINCT ga.id) AS address_count
                FROM gas_station_address ga
                WHERE ga.gas_station_id = :stationId
                """;

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("stationId", stationId);
        params.addValue("startDate", Date.valueOf(startDate));
        params.addValue("endDate", Date.valueOf(endDate));

        GasStationSummary summary = new GasStationSummary();
        summary.setId(stationId);

        Integer numberOfAddresses = namedParameterJdbcTemplate.queryForObject(countAddressesSql, params, Integer.class);
        summary.setNumberOfAddresses(numberOfAddresses);

        List<OilTypePriceData> dataList = namedParameterJdbcTemplate.query(dataSql, params, (rs, rowNum) -> {
            OilTypePriceData data = new OilTypePriceData();
            data.setType(rs.getString("type"));
            Double price = rs.getDouble("price");
            if (rs.wasNull()) {
                price = null;
            }
            data.setPrice(price);
            data.setDate(rs.getDate("date").toLocalDate());
            return data;
        });

        List<OilTypeAverageWidgets> widgetsList = namedParameterJdbcTemplate.query(avgSql, params, (rs, rowNum) -> {
            OilTypeAverageWidgets widget = new OilTypeAverageWidgets();
            widget.setType(rs.getString("type"));
            widget.setAveragePrice(rs.getDouble("average_price"));
            return widget;
        });

        summary.setData(dataList);
        summary.setWidgets(widgetsList);

        return summary;
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
