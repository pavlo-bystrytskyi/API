package org.example.api.dto;

import org.example.api.model.Character;
import org.example.api.model.Info;

import java.util.List;

public record RickMortyApiResponse(
        Info info,
        List<Character> results
) {
}
