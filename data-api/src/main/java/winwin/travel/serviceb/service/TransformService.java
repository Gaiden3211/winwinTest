package winwin.travel.serviceb.service;

import org.springframework.stereotype.Service;

@Service
public class TransformService {

    public String transform(String input) {
        if (input == null || input.isBlank()) {
            return input;
        }
        return new StringBuilder(input).reverse().toString().toUpperCase();
    }
}
