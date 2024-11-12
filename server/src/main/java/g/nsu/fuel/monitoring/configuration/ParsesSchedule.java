package g.nsu.fuel.monitoring.configuration;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ParsesSchedule {


    @Scheduled(cron = "0 0 * * * ?")
    public void performHourlyTask() {
    }
}
