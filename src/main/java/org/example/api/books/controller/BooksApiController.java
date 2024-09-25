package org.example.api.books.controller;

//As a bonus, another task to integrate an external API.
//Integrate the Book API from https://my-json-server.typicode.com/Flooooooooooorian/BookApi/books into your BookLibrary project.
//For example, GET https://my-json-server.typicode.com/Flooooooooooorian/BookApi/books/978-3-8362-8745-6.

import org.example.api.books.dto.Book;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BooksApiController {

    @GetMapping
    public List<Book> getAllBooks() {
        RestClient restClient = RestClient.builder().baseUrl("https://my-json-server.typicode.com/Flooooooooooorian/BookApi").build();
        Book[] response = restClient.get().uri("/books").retrieve().body(Book[].class);
        if (response == null) {
            throw new RuntimeException("Empty response");
        }
        return Arrays.asList(response);
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable String id) {
        RestClient restClient = RestClient.builder().baseUrl("https://my-json-server.typicode.com/Flooooooooooorian/BookApi").build();
        Book bookResponse = restClient.get().uri("/books/{id}",id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
            throw new RuntimeException(String.valueOf(response.getStatusCode()));
        }).body(Book.class);
        if (bookResponse == null) {
            throw new RuntimeException("Empty response");
        }
        return bookResponse;
    }

    @ExceptionHandler
    public ResponseEntity<String> noSuchElementExceptionHandler(Exception exception) {
        return new ResponseEntity<>(
                exception.getMessage(),
                HttpStatus.NOT_FOUND
        );
    }
}
