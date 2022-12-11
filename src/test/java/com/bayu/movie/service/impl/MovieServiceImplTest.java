package com.bayu.movie.service.impl;

import com.bayu.movie.dto.CreateMovieRequest;
import com.bayu.movie.dto.MovieResponse;
import com.bayu.movie.dto.UpdateMovieRequest;
import com.bayu.movie.exception.ResourceNotFoundException;
import com.bayu.movie.model.Movie;
import com.bayu.movie.service.MovieService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MovieServiceImplTest {

    private final static Logger log = LoggerFactory.getLogger(MovieServiceImplTest.class);

    @Autowired
    MovieService movieService;

    @Test
    void addNewMovie() {
        CreateMovieRequest createMovieRequest = CreateMovieRequest.builder()
                .title("Avatar")
                .description("This is avatar movie description")
                .rating(7.7f)
                .image("")
                .build();

        Movie movie = movieService.addNewMovie(createMovieRequest);

        assertNotNull(movie.getId());
        assertNotNull(movie.getCreatedAt());
        assertSame(createMovieRequest.getTitle(), movie.getTitle());

        log.info("ID: {}", movie.getId());
    }

    @Test
    void updateMovie() {
        Integer id = 4;
        UpdateMovieRequest updateMovieRequest = UpdateMovieRequest.builder()
                .title("Avatar movie update")
                .description("Avatar description update")
                .rating(9f)
                .image("avatar-img")
                .build();

        Movie movie = movieService.updateMovie(id, updateMovieRequest);

        assertNotNull(movie.getUpdatedAt());
        assertNotSame(movie.getCreatedAt(), movie.getUpdatedAt());
        assertSame(id, movie.getId());

        log.info("Updated At: {}", movie.getUpdatedAt());
    }

    @Test
    void getMovieDetail() {
        Integer id = 4;
        Movie movieDetail = movieService.getMovieDetail(id);

        assertNotNull(movieDetail);
        assertSame(id, movieDetail.getId());
    }

    @Test
    void getAllMovies() {
        Integer totalSampleData = 2;
        List<Movie> movies = movieService.getAllMovies();

        assertEquals(totalSampleData, movies.size());
    }

    @Test
    void deleteMovie() {
        Integer id = 4;
        movieService.deleteMovie(id);
        assertThrows(ResourceNotFoundException.class, () -> {
            Movie movieDetail = movieService.getMovieDetail(id);
        });
    }
}