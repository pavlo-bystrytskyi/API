package org.example.api.rickmorty.controller;

import lombok.RequiredArgsConstructor;
import org.example.api.rickmorty.dto.Character;
import org.example.api.rickmorty.dto.RickMortyApiResponse;
import org.example.api.rickmorty.service.ApiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    private final ApiService apiService;

    @GetMapping("/characters/{id}")
    public Character getCharacterById(@PathVariable String id) {
        Character response = this.apiService.getCharacterById(id);
        if (response == null) {
            throw new RuntimeException("Empty response");
        }

        return response;
    }

    @GetMapping("/characters")
    public List<Character> getCharactersWithStatus(@RequestParam(required = false) String status) {
        RickMortyApiResponse response = status != null
                ? this.apiService.getCharactersWithStatus(status)
                : this.apiService.getAllCharacters();
        if (response == null || response.results() == null) {
            throw new RuntimeException("Empty response");
        }

        return response.results();
    }

    @GetMapping("/species-statistic")
    public int getSpeciesStatistic(@RequestParam String species) {
        RickMortyApiResponse response = this.apiService.getSpeciesStatistic(species);
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


