package org.example.api.controller;

import org.example.api.dto.RickMortyApiResponse;
import org.example.api.model.Character;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@RequestMapping("/api/characters")
public class ApiController {

    @GetMapping
    public List<Character> getAllCharacters() {
        RestClient restClient = RestClient.builder().baseUrl("https://rickandmortyapi.com/api").build();
        RickMortyApiResponse response = restClient.get().uri("/character").retrieve().body(RickMortyApiResponse.class);
        if (response == null || response.results() == null) {
            throw new RuntimeException("Empty response");
        }

        return response.results();
    }
}
