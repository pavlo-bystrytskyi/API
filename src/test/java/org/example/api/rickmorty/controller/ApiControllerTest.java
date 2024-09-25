package org.example.api.rickmorty.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureMockRestServiceServer
class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MockRestServiceServer mockServer;

    @Test
    public void getCharacterById_expectOneCharacter() throws Exception {
        mockServer.expect(requestTo("https://rickandmortyapi.com/api/character/1"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("""
                             {
                                 "id": 1,
                                 "name": "John",
                                 "status": "Alive",
                                 "species": "Human"
                             }
                             """,
                        MediaType.APPLICATION_JSON));


        mockMvc.perform(MockMvcRequestBuilders.get("/api/characters"))
                .andExpect(status().isOk())
                .andExpect(content().json("""                   
                             {
                                 "id": 1,
                                 "name": "John",
                                 "status": "Alive",
                                 "species": "Human"
                             }
                        """));
    }

}