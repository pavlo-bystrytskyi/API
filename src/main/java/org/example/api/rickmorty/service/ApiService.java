package org.example.api.rickmorty.service;

import org.example.api.rickmorty.dto.Character;
import org.example.api.rickmorty.dto.RickMortyApiResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ApiService {

    RestClient restClient;

    public ApiService(RestClient.Builder clientBuilder) {
        this.restClient = RestClient.builder().baseUrl("https://rickandmortyapi.com/api").build();
    }

    public RickMortyApiResponse getAllCharacters() {
        return restClient.get().uri("/character").retrieve().body(RickMortyApiResponse.class);
    }

    public Character getCharacterById(String id) {
        return restClient.get().uri("/character/{id}", id).retrieve().body(Character.class);
    }

    public RickMortyApiResponse getCharactersWithStatus(String status) {
        return restClient.get().uri("/character?status={status}", status).retrieve().body(RickMortyApiResponse.class);
    }

    public RickMortyApiResponse getSpeciesStatistic(String species) {
        return restClient.get()
                .uri("/character?species={species}", species)
                .retrieve()
                .body(RickMortyApiResponse.class);
    }
}
