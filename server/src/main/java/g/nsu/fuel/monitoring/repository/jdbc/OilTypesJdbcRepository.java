package g.nsu.fuel.monitoring.repository.jdbc;

import g.nsu.fuel.monitoring.payload.response.OilTypeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Slf4j
public class OilTypesJdbcRepository {


    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public OilTypesJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
    }

    public List<OilTypeResponse> getAllOilTypes() {
        MapSqlParameterSource params = new MapSqlParameterSource();
        String sql = """
            SELECT type FROM oil_types
            """;
        return namedParameterJdbcTemplate.query(sql, params, (rs, rowNum) -> {
            OilTypeResponse oilTypeResponse= new OilTypeResponse();
            oilTypeResponse.setOilType(rs.getString("type"));
            return oilTypeResponse;
        });
    }
}
