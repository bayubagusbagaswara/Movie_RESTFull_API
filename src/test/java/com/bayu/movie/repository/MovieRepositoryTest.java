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
import java.util.List;
import java.util.Optional;

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
        Integer id = 2;
        Optional<Movie> movie = movieRepository.findById(id);

        assertTrue(movie.isPresent(), "Product is present with id : " + id);
    }

    @Test
    void getMovieByIdNotFound() {
        Integer id = 5;
        Optional<Movie> movie = movieRepository.findById(id);

        assertFalse(movie.isPresent(), "Product is not found with id : " + id);
    }

    @Test
    void getAllMovies() {
        List<Movie> movieList = movieRepository.findAll();
        assertEquals(2, movieList.size());
    }

    @Test
    void updateMovie() {
        Integer id = 2;

        Optional<Movie> movie = movieRepository.findById(id);
        assertTrue(movie.isPresent(), "Movie is present with id : " + id);

        Movie movieUpdate = movie.get();

        movieUpdate.setTitle("Movie 2 update");
        movieUpdate.setDescription("Description 2 update");
        movieUpdate.setRating(9f);
        movieUpdate.setImage("image-2-update");
        movieUpdate.setUpdatedAt(LocalDateTime.now());

        movieRepository.save(movieUpdate);

        assertSame(movie.get().getId(), movieUpdate.getId());
        assertNotNull(movieUpdate.getUpdatedAt());
        assertNotEquals(movie.get().getCreatedAt(), movieUpdate.getUpdatedAt());
    }

    @Test
    void deleteMovie() {
        Integer id = 3;
        Optional<Movie> movieOptional = movieRepository.findById(id);
        assertTrue(movieOptional.isPresent(), "Movie is present");

        Movie movie = movieOptional.get();
        movieRepository.delete(movie);

        Optional<Movie> movieDeleted = movieRepository.findById(id);
        assertFalse(movieDeleted.isPresent(), "Movie not found");
    }

}