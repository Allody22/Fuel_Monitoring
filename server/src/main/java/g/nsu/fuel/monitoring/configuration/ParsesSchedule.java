package g.nsu.fuel.monitoring.configuration;

import g.nsu.fuel.monitoring.repository.GasStationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@AllArgsConstructor
@Service
public class ParsesSchedule {

    private final GasStationRepository gasStationRepository;

    private final RestTemplate restTemplate;

//    private final List<String> gasStationName = new ArrayList{"wqeqw","ewq};

    @Scheduled(cron = "0 0 0 * * ?")
    public void performScheduledTask() {
//        String network = yourDatabaseService.getLatestNetworkParameter(); // метод, который возвращает параметр из базы данных
//
//        String url = "http://127.0.0.1:5000/api/update_prices/" + "gasprom";
//        log.info("time for request {}",url);
//        try {
//            String response = restTemplate.postForObject(url, null, String.class);
//            System.out.println("Response from server: " + response);
//        } catch (Exception e) {
//            System.err.println("Error performing scheduled task: " + e.getMessage());
//        }
    }
}
