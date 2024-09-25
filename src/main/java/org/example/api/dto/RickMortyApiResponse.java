package org.example.api.dto;

import org.example.api.model.Character;

import java.util.List;

public record RickMortyApiResponse(List<Character> results) {
}
