package com.bayu.movie.controller;

import com.bayu.movie.dto.*;
import com.bayu.movie.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MovieControllerTest {

    private final static Logger log = LoggerFactory.getLogger(MovieControllerTest.class);

    @Autowired
    MovieController movieController;

    @Test
    void listOfMovie() {
        ResponseEntity<WebResponse<List<MovieResponse>>> responseEntity = movieController.listOfMovie();

        assertEquals(Boolean.TRUE, Objects.requireNonNull(responseEntity.getBody()).getSuccess());
        assertEquals(1, responseEntity.getBody().getData().size());
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    void detailOfMovie() {
//        Integer id = 2;
//        ResponseEntity<WebResponse<MovieResponse>> responseEntity = movieController.detailOfMovie();
//
//        assertEquals(Boolean.TRUE, Objects.requireNonNull(responseEntity.getBody()).getSuccess());
//        assertNotNull(responseEntity.getBody());
//        assertSame(id, responseEntity.getBody().getData().getId());
//
//        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    void addNewMovie() {
        CreateMovieRequest createMovieRequest = CreateMovieRequest.builder()
                .title("Harry Potter")
                .description("This is Harry Potter description")
                .rating(8f)
                .image("")
                .build();

        ResponseEntity<WebResponse<MovieResponse>> responseEntity = movieController.addNewMovie(createMovieRequest);

        assertEquals(Boolean.TRUE, Objects.requireNonNull(responseEntity.getBody()).getSuccess());
        assertNotNull(responseEntity.getBody().getData().getId());
        assertEquals(201, responseEntity.getStatusCodeValue());

        log.info("ID: {}", responseEntity.getBody().getData().getId());
    }

    @Test
    void updateMovie() {
        Integer id = 2;
        UpdateMovieRequest updateMovieRequest = UpdateMovieRequest.builder()
                .title("Harry Potter update")
                .description("This is Harry Potter update description")
                .rating(6.8f)
                .image("img-update")
                .build();

        ResponseEntity<WebResponse<MovieResponse>> responseEntity = movieController.updateMovie(id, updateMovieRequest);

        assertEquals(Boolean.TRUE, Objects.requireNonNull(responseEntity.getBody()).getSuccess());
        assertNotNull(responseEntity.getBody().getData().getUpdatedAt());
        assertEquals(id, responseEntity.getBody().getData().getId());
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    void deleteMovie() {
        Integer id = 1;
        ResponseEntity<WebResponse<String>> responseEntity = movieController.deleteMovie(id);

        assertEquals(Boolean.TRUE, Objects.requireNonNull(responseEntity.getBody()).getSuccess());
        assertEquals(200, responseEntity.getStatusCodeValue());

        assertThrows(ResourceNotFoundException.class, () -> {
            ResponseEntity<WebResponse<String>> response = movieController.deleteMovie(id);
        });
    }
}