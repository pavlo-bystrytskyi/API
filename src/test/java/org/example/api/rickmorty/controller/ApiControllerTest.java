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
                                    "species": "Human"
                                }
                                """,
                        MediaType.APPLICATION_JSON));


        mockMvc.perform(MockMvcRequestBuilders.get("/api/characters/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("""                   
                             {
                                 "id": 1,
                                 "name": "John",
                                 "species": "Human"
                             }
                        """));
    }

    @Test
    public void getCharactersWithStatus_getAll() throws Exception {
        mockServer.expect(requestTo("https://rickandmortyapi.com/api/character"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("""
                                   {
                                     "info": {
                                       "count": 2
                                     },
                                     "results": [
                                       {
                                         "id": 1,
                                         "name": "John",
                                         "species": "Human"
                                       },
                                       {
                                         "id": 2,
                                         "name": "Jane",
                                         "species": "Human"
                                       }
                                     ]
                                   }
                                """,
                        MediaType.APPLICATION_JSON));


        mockMvc.perform(MockMvcRequestBuilders.get("/api/characters"))
                .andExpect(status().isOk())
                .andExpect(content().json("""       
                        [            
                            {
                                "id": 1,
                                "name": "John",
                                "species": "Human"
                            },  
                            {
                                "id": 2,
                                "name": "Jane",
                                "species": "Human"
                            }
                        ]
                        """));
    }


    @Test
    public void getCharactersWithStatus_getByStatus() throws Exception {
        mockServer.expect(requestTo("https://rickandmortyapi.com/api/character?status=alive"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("""
                                   {
                                     "info": {
                                       "count": 2
                                     },
                                     "results": [
                                       {
                                         "id": 1,
                                         "name": "John",
                                         "species": "Human"
                                       },
                                       {
                                         "id": 2,
                                         "name": "Jane",
                                         "species": "Human"
                                       }
                                     ]
                                   }
                                """,
                        MediaType.APPLICATION_JSON));


        mockMvc.perform(MockMvcRequestBuilders.get("/api/characters?status=alive"))
                .andExpect(status().isOk())
                .andExpect(content().json("""       
                        [            
                            {
                                "id": 1,
                                "name": "John",
                                "species": "Human"
                            },  
                            {
                                "id": 2,
                                "name": "Jane",
                                "species": "Human"
                            }
                        ]
                        """));
    }

    @Test
    public void getSpeciesStatistic_getHumans() throws Exception {
        mockServer.expect(requestTo("https://rickandmortyapi.com/api/character?species=human"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("""
                                   {
                                     "info": {
                                       "count": 2
                                     },
                                     "results": [
                                       {
                                         "id": 1,
                                         "name": "John",
                                         "species": "Human"
                                       },
                                       {
                                         "id": 2,
                                         "name": "Jane",
                                         "species": "Human"
                                       }
                                     ]
                                   }
                                """,
                        MediaType.APPLICATION_JSON));


        mockMvc.perform(MockMvcRequestBuilders.get("/api/species-statistic?species=human"))
                .andExpect(status().isOk())
                .andExpect(content().string("2"));
    }

}