package com.bayu.movie.controller;

import com.bayu.movie.dto.*;
import com.bayu.movie.service.MovieService;
import com.bayu.movie.util.ValidationUtil;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;
    private final ValidationUtil validationUtil;

    public MovieController(MovieService movieService, ValidationUtil validationUtil) {
        this.movieService = movieService;
        this.validationUtil = validationUtil;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<MovieResponse>> listOfMovie() {
        List<MovieResponse> movies = movieService.getAllMovies();
        return WebResponse.<List<MovieResponse>>builder()
                .success(Boolean.TRUE)
                .message("Successfully get all list of movie")
                .data(movies)
                .build();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<MovieResponse> detailOfMovie(@PathVariable(name = "id") Integer id) {
        MovieResponse movieDetail = movieService.getMovieDetail(id);
        return WebResponse.<MovieResponse>builder()
                .success(Boolean.TRUE)
                .message("Successfully get detail of movie with id : " + id)
                .data(movieDetail)
                .build();
    }

    @PostMapping
    public WebResponse<MovieResponse> addNewMovie(@RequestBody CreateMovieRequest createMovieRequest) {
        validationUtil.validate(createMovieRequest);
        MovieResponse movie = movieService.addNewMovie(createMovieRequest);
        return WebResponse.<MovieResponse>builder()
                .success(Boolean.TRUE)
                .message("Successfully add new movie with id : " + movie.getId())
                .data(movie)
                .build();
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<MovieResponse> updateMovie(@PathVariable(name = "id") Integer id, @RequestBody UpdateMovieRequest updateMovieRequest) {
        validationUtil.validate(updateMovieRequest);
        MovieResponse movie = movieService.updateMovie(id, updateMovieRequest);
        return WebResponse.<MovieResponse>builder()
                .success(Boolean.TRUE)
                .message("Successfully update movie with id : " + id)
                .data(movie)
                .build();
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MessageResponse deleteMovie(@PathVariable(name = "id") Integer id) {
        movieService.deleteMovie(id);
        return MessageResponse.builder()
                .success(Boolean.TRUE)
                .message("Successfully delete movie with id : " + id)
                .build();
    }

}
