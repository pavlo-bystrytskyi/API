package org.example.api.rickmorty.dto;

import org.example.api.rickmorty.model.Character;
import org.example.api.rickmorty.model.Info;

import java.util.List;

public record RickMortyApiResponse(
        Info info,
        List<Character> results
) {
}
