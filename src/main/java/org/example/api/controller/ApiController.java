package org.example.api.controller;

import org.example.api.dto.RickMortyApiResponse;
import org.example.api.model.Character;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping("/characters")
    public List<Character> getAllCharacters() {
        RestClient restClient = RestClient.builder().baseUrl("https://rickandmortyapi.com/api").build();
        RickMortyApiResponse response = restClient.get().uri("/character").retrieve().body(RickMortyApiResponse.class);
        if (response == null || response.results() == null) {
            throw new RuntimeException("Empty response");
        }

        return response.results();
    }

    @GetMapping("/characters/{id}")
    public Character getCharacterById(@PathVariable String id) {
        RestClient restClient = RestClient.builder().baseUrl("https://rickandmortyapi.com/api").build();
        Character response = restClient.get().uri("/character/{id}", id).retrieve().body(Character.class);
        if (response == null) {
            throw new RuntimeException("Empty response");
        }

        return response;
    }

    @GetMapping("/characters/status")
    public List<Character> getCharactersWithStatus(@RequestParam String status) {
        RestClient restClient = RestClient.builder().baseUrl("https://rickandmortyapi.com/api").build();
        RickMortyApiResponse response = restClient.get().uri("/character?status={status}", status).retrieve().body(RickMortyApiResponse.class);
        if (response == null || response.results() == null) {
            throw new RuntimeException("Empty response");
        }

        return response.results();
    }

    @GetMapping("/species-statistic")
    public int getSpeciesStatistic(@RequestParam String species) {
        RestClient restClient = RestClient.builder().baseUrl("https://rickandmortyapi.com/api").build();
        RickMortyApiResponse response = restClient.get()
                .uri("/character?species={species}", species)
                .retrieve()
                .body(RickMortyApiResponse.class);
        if (response == null || response.info() == null) {
            throw new RuntimeException("Empty response");
        }

        return response.info().count();
    }

    @ExceptionHandler
    public ResponseEntity<String> noSuchElementExceptionHandler(Exception exception) {
        return new ResponseEntity<>(
                exception.getMessage(),
                HttpStatus.NOT_FOUND
        );
    }
}


