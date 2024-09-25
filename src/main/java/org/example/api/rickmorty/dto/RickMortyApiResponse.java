package org.example.api.rickmorty.dto;

import java.util.List;

public record RickMortyApiResponse(
        Info info,
        List<Character> results
) {
}
