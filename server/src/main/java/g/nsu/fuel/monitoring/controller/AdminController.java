package g.nsu.fuel.monitoring.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@Tag(name = "00. Auth Controller", description = "API для администратора..")
@RequestMapping("/api/v1/admin")
public class AdminController {


}
