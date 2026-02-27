package winwin.travel.servicea.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import winwin.travel.servicea.service.ProcessService;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class ProcessController {

    private final ProcessService processService;

    public ProcessController(ProcessService processService) {
        this.processService = processService;
    }

    @PostMapping("/process")
    public Map<String, String> process(@RequestBody Map<String, String> request) {
        String input = request.get("text");
        String result = processService.processText(input);

        return Map.of("result", result);
    }
}