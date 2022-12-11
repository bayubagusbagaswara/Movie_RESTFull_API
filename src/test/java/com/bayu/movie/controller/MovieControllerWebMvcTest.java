package com.bayu.movie.controller;

import com.bayu.movie.model.Movie;
import com.bayu.movie.service.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MovieController.class)
public class MovieControllerWebMvcTest {

    @MockBean
    private MovieService movieService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createMovie() throws Exception {
        Movie movie = new Movie();
        movie.setId(100);
        movie.setTitle("KungFu Panda");
        movie.setDescription("This is KungFu Panda description");
        movie.setRating(8.8f);
        movie.setImage("img-kungfu");
        movie.setCreatedAt(LocalDateTime.now());

        when(movieService.addNewMovie(ArgumentMatchers.any())).thenReturn(movie);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/movies")
                        .content(objectMapper.writeValueAsString(movie))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.data.id").exists())
                .andExpect(jsonPath("$.data.title").value("KungFu Panda"))
                .andExpect(jsonPath("$.data.description").value("This is KungFu Panda description"))
                .andExpect(jsonPath("$.data.rating").value("8.8"))
                .andExpect(jsonPath("$.data.image").value("img-kungfu"))
                .andExpect(status().isCreated())
                .andDo(print());

    }

    @Test
    void getMovieById() throws Exception {

        Movie movie = new Movie();
        movie.setTitle("Pengabdi Setan");
        movie.setDescription("Deskripsi Pengabdi Setan");
        movie.setRating(7f);
        movie.setImage("url-image");
        movie.setCreatedAt(LocalDateTime.now());
        movie.setUpdatedAt(LocalDateTime.now());

        when(movieService.getMovieDetail(anyInt())).thenReturn(movie);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/movies/{id}", 3)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.data.title").value("Pengabdi Setan"))
                .andExpect(jsonPath("$.data.description").value("Deskripsi Pengabdi Setan"))
                .andExpect(jsonPath("$.data.rating").value("7.0"))
                .andExpect(jsonPath("$.data.image").value("url-image"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
