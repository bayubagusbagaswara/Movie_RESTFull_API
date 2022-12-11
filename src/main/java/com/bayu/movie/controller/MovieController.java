package com.bayu.movie.controller;

import com.bayu.movie.dto.*;
import com.bayu.movie.model.Movie;
import com.bayu.movie.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<List<MovieResponse>>> listOfMovie() {
        List<Movie> movieList = movieService.getAllMovies();
        List<MovieResponse> movieResponses = mapFromMovieList(movieList);
        WebResponse<List<MovieResponse>> webResponse = WebResponse.<List<MovieResponse>>builder()
                .success(Boolean.TRUE)
                .message("Successfully get all list of movie")
                .data(movieResponses)
                .build();

        return new ResponseEntity<>(webResponse, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<MovieResponse>> detailOfMovie(@PathVariable(name = "id") Integer id) {
        Movie movie = movieService.getMovieDetail(id);
        MovieResponse movieDetail = mapFromMovie(movie);
        WebResponse<MovieResponse> webResponse = WebResponse.<MovieResponse>builder()
                .success(Boolean.TRUE)
                .message("Successfully get detail of movie with id : " + id)
                .data(movieDetail)
                .build();
        return new ResponseEntity<>(webResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<WebResponse<MovieResponse>> addNewMovie(@Valid @RequestBody CreateMovieRequest createMovieRequest) {
        Movie movie = movieService.addNewMovie(createMovieRequest);
        MovieResponse movieResponse = mapFromMovie(movie);
        WebResponse<MovieResponse> webResponse = WebResponse.<MovieResponse>builder()
                .success(Boolean.TRUE)
                .message("Successfully add new movie with id : " + movie.getId())
                .data(movieResponse)
                .build();

        return new ResponseEntity<>(webResponse, HttpStatus.CREATED);
    }

    @PatchMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<MovieResponse>> updateMovie(@PathVariable(name = "id") Integer id, @Valid @RequestBody UpdateMovieRequest updateMovieRequest) {
        Movie movie = movieService.updateMovie(id, updateMovieRequest);
        MovieResponse movieResponse = mapFromMovie(movie);
        WebResponse<MovieResponse> webResponse = WebResponse.<MovieResponse>builder()
                .success(Boolean.TRUE)
                .message("Successfully update movie with id : " + id)
                .data(movieResponse)
                .build();

        return new ResponseEntity<>(webResponse, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<String>> deleteMovie(@PathVariable(name = "id") Integer id) {
        movieService.deleteMovie(id);
        WebResponse<String> webResponse = WebResponse.<String>builder()
                .success(Boolean.TRUE)
                .message("Successfully delete movie with id : " + id)
                .build();

        return new ResponseEntity<>(webResponse, HttpStatus.OK);
    }

    private MovieResponse mapFromMovie(Movie movie) {
        return MovieResponse.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .rating(movie.getRating())
                .image(movie.getImage())
                .createdAt(String.valueOf(movie.getCreatedAt()))
                .updatedAt(String.valueOf(movie.getUpdatedAt()))
                .build();
    }

    private List<MovieResponse> mapFromMovieList(List<Movie> movies) {
        return movies.stream()
                .map(this::mapFromMovie)
                .collect(Collectors.toList());
    }

}
