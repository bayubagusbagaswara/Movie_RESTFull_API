package com.bayu.movie.repository;

import com.bayu.movie.model.Movie;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class MovieRepositoryTest {

    private final static Logger log = LoggerFactory.getLogger(MovieRepositoryTest.class);

    @Autowired
    MovieRepository movieRepository;

    @Test
    void saveMovie() {
        Movie movie = Movie.builder()
                .title("Movie 1")
                .description("This is Movie 1 description")
                .rating(5.0f)
                .image("url-image1")
                .createdAt(LocalDateTime.now())
                .build();

        movieRepository.save(movie);

        assertNotNull(movie.getId());

        log.info("ID: {}", movie.getId());
    }

    @Test
    void getMovieById() {
    }

    @Test
    void getAllMovies() {
    }

    @Test
    void updateMovie() {

    }

    @Test
    void deleteMovie() {

    }
}