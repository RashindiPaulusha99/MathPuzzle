package com.uob.mathpuzzle.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.uob.mathpuzzle.dto.GameResDto;
import com.uob.mathpuzzle.exception.GameException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Log4j2
public class TomatoAPI {

    private static final String API_URL = "https://marcconrad.com/uob/tomato/api.php?out=json";

    private final RestTemplate restTemplate;

    public GameResDto getNewQuestion() {

        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

            ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.GET, entity, String.class);

            String responseBody = response.getBody();
            assert responseBody != null;
            JsonObject json = new JsonParser().parse(responseBody).getAsJsonObject();

            String question = json.get("question").getAsString();
            int solution = json.get("solution").getAsInt();

            log.info("question URL  => {}", question);
            log.info("solution  => {}", solution);
            return new GameResDto(question, solution);
        } catch (HttpStatusCodeException e) {
            log.error("Invalid request: " + e.getResponseBodyAsString());
            throw new GameException("Oops something went wrong!");
        }
    }


}
