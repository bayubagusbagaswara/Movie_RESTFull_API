package com.bayu.movie.controller;

import com.bayu.movie.exception.ResourceNotFoundException;
import com.bayu.movie.model.Movie;
import com.bayu.movie.service.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MovieController.class)
public class MovieControllerWebMvcTest {

    private final static Logger log = LoggerFactory.getLogger(MovieControllerWebMvcTest.class);
    @MockBean
    private MovieService movieService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createMovie() throws Exception {
        Movie movie = new Movie();
        movie.setId(5);
        movie.setTitle("KungFu Panda");
        movie.setDescription("This is KungFu Panda description");
        movie.setRating(8.8f);
        movie.setImage("img-kungfu");
        movie.setCreatedAt(LocalDateTime.now());

        when(movieService.addNewMovie(ArgumentMatchers.any())).thenReturn(movie);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/movies")
                        .content(objectMapper.writeValueAsString(movie))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success", Matchers.equalTo(true)))
                .andExpect(jsonPath("$.message").isNotEmpty())
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
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success", Matchers.equalTo(true)))
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(jsonPath("$.data.title").value("Pengabdi Setan"))
                .andExpect(jsonPath("$.data.description").value("Deskripsi Pengabdi Setan"))
                .andExpect(jsonPath("$.data.rating").value("7.0"))
                .andExpect(jsonPath("$.data.image").value("url-image"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getAllMovies() throws Exception {
        List<Movie> movies = new ArrayList<>();
        Movie movie = new Movie();
        movie.setId(7);
        movie.setTitle("Laskar Pelangi");
        movie.setDescription("Laskar Pelangi adalah film karya Andrea Hirata");
        movie.setRating(9f);
        movie.setImage("img-laskar-pelangi");
        movie.setCreatedAt(LocalDateTime.now());
        movie.setUpdatedAt(LocalDateTime.now());

        Movie movie1 = new Movie();
        movie1.setId(8);
        movie1.setTitle("Sang Pemimpi");
        movie1.setDescription("Sang Pemimpi berkisah tentang perjuangan anak dalam meraih mimpinya");
        movie1.setRating(8.5f);
        movie1.setImage("img-pemimpi");
        movie1.setCreatedAt(LocalDateTime.now());
        movie1.setUpdatedAt(LocalDateTime.now());

        movies.add(movie);
        movies.add(movie1);

        when(movieService.getAllMovies()).thenReturn(movies);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/movies")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.data", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.success", Matchers.equalTo(true)))
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(jsonPath("$.data[0].title", Matchers.equalTo("Laskar Pelangi")))
                .andExpect(jsonPath("$.data[1].title", Matchers.equalTo("Sang Pemimpi")))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void updateMovie() throws Exception {
        Integer id = 1;
        Movie movie = Movie.builder()
                .id(id)
                .title("Laskar Pelangi")
                .description("Laskar Pelangi description")
                .rating(9f)
                .image("img-laskar")
                .createdAt(LocalDateTime.now())
                .build();

        Movie updateMovie = Movie.builder()
                .id(id)
                .title("Laskar Pelangi Update")
                .description("Laskar Pelangi description update")
                .rating(10f)
                .image("img-laskar-new")
                .updatedAt(LocalDateTime.now())
                .build();

        when(movieService.getMovieDetail(id)).thenReturn(movie);
        when(movieService.updateMovie(anyInt(), any())).thenReturn(updateMovie);

        mockMvc.perform(MockMvcRequestBuilders
            .patch("/movies/{id}", id)
                        .content(objectMapper.writeValueAsString(updateMovie))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.success", Matchers.equalTo(true)))
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(jsonPath("$.data.id", Matchers.equalTo(id)))
                .andExpect(jsonPath("$.data.title").value(updateMovie.getTitle()))
                .andExpect(jsonPath("$.data.updatedAt").value(String.valueOf(updateMovie.getUpdatedAt())))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void deleteMovie() throws Exception {
        mockMvc.perform(
                delete("/movies/{id}", 5)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", Matchers.equalTo(true)))
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(jsonPath("$.data").isEmpty())
                .andDo(print());
    }

    @Test
    void testBadRequest() throws Exception {
        Movie movie = Movie.builder()
                .title("")
                .description("Des")
                .rating(0f)
                .image("")
                .createdAt(LocalDateTime.now())
                .build();

        mockMvc.perform(MockMvcRequestBuilders
                .post("/movies")
                        .content(objectMapper.writeValueAsString(movie))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Bad Request"))
                .andExpect(jsonPath("$.data.title").value("Title must not be blank"))
                .andExpect(jsonPath("$.data.description").value("Description length minimum must be 10 characters and maximum must be 500 characters"))
                .andExpect(jsonPath("$.data.rating").value("Rating minimum is 1"))
                .andDo(print());
    }

    @Test
    void testMovieNotFound() throws Exception {
        Integer id = 20;

        when(movieService.getMovieDetail(id)).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/movies/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.data").isEmpty())
                .andDo(print());
    }
}
