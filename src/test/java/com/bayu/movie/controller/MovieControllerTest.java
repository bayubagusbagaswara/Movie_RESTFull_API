package com.bayu.movie.controller;

import com.bayu.movie.dto.*;
import com.bayu.movie.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MovieControllerTest {

    private final static Logger log = LoggerFactory.getLogger(MovieControllerTest.class);

    @Autowired
    MovieController movieController;

    @Test
    void listOfMovie() {
        WebResponse<List<MovieResponse>> movie = movieController.listOfMovie();

        assertEquals(Boolean.TRUE, movie.getSuccess());
        assertEquals(1, movie.getData().size());
    }

    @Test
    void detailOfMovie() {
        Integer id = 1;
        WebResponse<MovieResponse> movie = movieController.detailOfMovie(id);

        assertEquals(Boolean.TRUE, movie.getSuccess());
        assertNotNull(movie);
        assertSame(id, movie.getData().getId());
    }

    @Test
    void addNewMovie() {
        CreateMovieRequest createMovieRequest = CreateMovieRequest.builder()
                .title("Harry Potter")
                .description("This is Harry Potter description")
                .rating(8f)
                .image("")
                .build();

        WebResponse<MovieResponse> movie = movieController.addNewMovie(createMovieRequest);

        assertEquals(Boolean.TRUE, movie.getSuccess());
        assertNotNull(movie.getData().getId());

        log.info("ID: {}", movie.getData().getId());
    }

    @Test
    void updateMovie() {
        Integer id = 1;
        UpdateMovieRequest updateMovieRequest = UpdateMovieRequest.builder()
                .title("Harry Potter update")
                .description("This is Harry Potter update description")
                .rating(6.8f)
                .image("img-update")
                .build();

        WebResponse<MovieResponse> movie = movieController.updateMovie(id, updateMovieRequest);

        assertEquals(Boolean.TRUE, movie.getSuccess());
        assertNotNull(movie.getData().getUpdatedAt());
        assertSame(id, movie.getData().getId());
    }

    @Test
    void deleteMovie() {
        Integer id = 1;
        WebResponse<String> response = movieController.deleteMovie(id);

        assertEquals(Boolean.TRUE, response.getSuccess());

        assertThrows(ResourceNotFoundException.class, () -> {
            WebResponse<String> webResponse = movieController.deleteMovie(id);
        });
    }
}