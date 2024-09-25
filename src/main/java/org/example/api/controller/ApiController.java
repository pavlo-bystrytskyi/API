package org.example.api.controller;

//Write an endpoint that returns a Rick&Morty character. When you enter GET http://localhost:8080/api/characters/2 in Postman,
// the character with ID 2 should be returned.
//``{"id": 2, "name": "Rick Sanchez", "species": "Human"}

import org.example.api.dto.RickMortyApiResponse;
import org.example.api.model.Character;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/{id}")
    public Character getCharacterById(@PathVariable String id) {
        RestClient restClient = RestClient.builder().baseUrl("https://rickandmortyapi.com/api").build();
        Character response = restClient.get().uri("/character/{id}", id).retrieve().body(Character.class);
        if (response == null) {
            throw new RuntimeException("Empty response");
        }

        return response;
    }
}
