package g.nsu.fuel.monitoring.repository.jdbc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class GasStationAddressJdbcRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public GasStationAddressJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
    }

    public GasStationAddressSummary getSummary(Long stationId, LocalDate startDate, LocalDate endDate) {
        String dataSql = """
                WITH date_series AS (
                    SELECT generate_series(:startDate::date, :endDate::date, interval '1 day') AS date
                ),
                oil_types_list AS (
                    SELECT DISTINCT ot.type
                    FROM oil_types ot
                    JOIN oil_type_price_address_history p ON p.oil_type_id = ot.type
                    WHERE p.gas_station_address_id = :stationId
                      AND p.date BETWEEN :startDate AND :endDate
                )
                SELECT
                    d.date,
                    otl.type,
                    p.price
                FROM
                    date_series d
                CROSS JOIN
                    oil_types_list otl
                LEFT JOIN
                    oil_type_price_address_history p ON p.date = d.date
                        AND p.oil_type_id = otl.type
                        AND p.gas_station_address_id = :stationId
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
                WHERE
                    p.gas_station_address_id = :stationId
                    AND p.date BETWEEN :startDate AND :endDate
                GROUP BY
                    ot.type
                """;

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("stationId", stationId);
        params.addValue("startDate", Date.valueOf(startDate));
        params.addValue("endDate", Date.valueOf(endDate));

        GasStationAddressSummary summary = new GasStationAddressSummary();

        List<OilTypePriceData> dataList = namedParameterJdbcTemplate.query(dataSql, params, (rs, rowNum) -> {
            OilTypePriceData data = new OilTypePriceData();
            data.setType(rs.getString("type"));
            Double price = rs.getDouble("price");
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

}
