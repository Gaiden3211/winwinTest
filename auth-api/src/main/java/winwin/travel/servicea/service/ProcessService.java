package winwin.travel.servicea.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import winwin.travel.servicea.dto.ServiceBResponse;
import winwin.travel.servicea.entity.ProcessingLog;
import winwin.travel.servicea.repository.ProcessingLogRepository;
import winwin.travel.servicea.repository.UserRepository;
import winwin.travel.servicea.entity.User;

import java.util.Map;

@Service
public class ProcessService {

    private final ProcessingLogRepository logRepository;
    private final UserRepository userRepository;
    private final RestClient restClient;

    @Value("${app.data-api.url}")
    private String dataApiUrl;

    @Value("${app.internal-token}")
    private String internalToken;

    public ProcessService(ProcessingLogRepository logRepository, UserRepository userRepository) {
        this.logRepository = logRepository;
        this.userRepository = userRepository;
        this.restClient = RestClient.create();
    }

    public String processText(String inputText) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User is not authenticated");
        }

         String email = (String) authentication.getPrincipal();


        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found in database"));

        ServiceBResponse response = restClient.post()
                .uri(dataApiUrl)
                .header("X-Internal-Token", internalToken)
                .body(Map.of("text", inputText))
                .retrieve()
                .body(ServiceBResponse.class);

        String resultText = (response != null) ? response.result() : "Error: No response";

        ProcessingLog log = new ProcessingLog();
        log.setUser(user);
        log.setInputText(inputText);
        log.setOutputText(resultText);
        logRepository.save(log);

        return resultText;
    }
}