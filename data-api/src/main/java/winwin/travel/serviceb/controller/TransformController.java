package winwin.travel.serviceb.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import winwin.travel.serviceb.dto.TransformRequest;
import winwin.travel.serviceb.dto.TransformResponse;
import winwin.travel.serviceb.service.TransformService;


@RestController
@RequestMapping("/api")
public class TransformController {

    private final TransformService transformService;
    private final String expectedInternalToken;


    public TransformController(
            TransformService transformService,
            // Тепер ми беремо значення з application.properties
            @Value("${app.internal-token}") String expectedInternalToken) {
        this.transformService = transformService;
        this.expectedInternalToken = expectedInternalToken;
    }

    @PostMapping("/transform")
    public ResponseEntity<?> transformText(
            @RequestHeader(value = "X-Internal-Token", required = false) String providedToken,
            @RequestBody TransformRequest request) {

        //Перевірка "Server-to-Server" токена
        if (providedToken == null || !providedToken.equals(expectedInternalToken)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Error: Missing or invalid X-Internal-Token");
        }

        if (request.text() == null) {
            return ResponseEntity.badRequest().body("Error: Text field cannot be null");
        }

        String transformedText = transformService.transform(request.text());

        return ResponseEntity.ok(new TransformResponse(transformedText));
    }
}
