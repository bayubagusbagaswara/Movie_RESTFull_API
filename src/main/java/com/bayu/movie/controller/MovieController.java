package com.bayu.movie.controller;

import com.bayu.movie.dto.*;
import com.bayu.movie.exception.AppException;
import com.bayu.movie.exception.BadRequestException;
import com.bayu.movie.exception.ResourceNotFoundException;
import com.bayu.movie.model.Movie;
import com.bayu.movie.service.MovieService;
import com.bayu.movie.util.ValidationUtil;
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
//    private final ValidationUtil validationUtil;
//
//    public MovieController(MovieService movieService, ValidationUtil validationUtil) {
//        this.movieService = movieService;
//        this.validationUtil = validationUtil;
//    }


    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<List<MovieResponse>>> listOfMovie() {
        try {
            List<Movie> allMovies = movieService.getAllMovies();
            List<MovieResponse> movies = allMovies.stream()
                    .map(x -> MovieResponse.builder()
                            .id(x.getId())
                            .title(x.getTitle())
                            .description(x.getDescription())
                            .rating(x.getRating())
                            .image(x.getImage())
                            .createdAt(String.valueOf(x.getCreatedAt()))
                            .updatedAt(String.valueOf(x.getUpdatedAt()))
                            .build()).collect(Collectors.toList());
            WebResponse<List<MovieResponse>> webResponse = WebResponse.<List<MovieResponse>>builder()
                    .success(Boolean.TRUE)
                    .message("Successfully get all list of movie")
                    .data(movies)
                    .build();

            return new ResponseEntity<>(webResponse, HttpStatus.OK);
        } catch (AppException e) {
            WebResponse<List<MovieResponse>> webResponse = WebResponse.<List<MovieResponse>>builder()
                    .success(Boolean.FALSE)
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(webResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<MovieResponse>> detailOfMovie(@PathVariable(name = "id") Integer id) {

        try {
            Movie movie = movieService.getMovieDetail(id);
            MovieResponse movieDetail = mapFromMovie(movie);
            WebResponse<MovieResponse> webResponse = WebResponse.<MovieResponse>builder()
                    .success(Boolean.TRUE)
                    .message("Successfully get detail of movie with id : " + id)
                    .data(movieDetail)
                    .build();
            return new ResponseEntity<>(webResponse, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            WebResponse<MovieResponse> webResponse = WebResponse.<MovieResponse>builder()
                    .success(Boolean.FALSE)
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(webResponse, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<WebResponse<MovieResponse>> addNewMovie(@Valid @RequestBody CreateMovieRequest createMovieRequest) {
        try {
//            validationUtil.validate(createMovieRequest);
            Movie movie = movieService.addNewMovie(createMovieRequest);
            MovieResponse movieResponse = mapFromMovie(movie);
            WebResponse<MovieResponse> webResponse = WebResponse.<MovieResponse>builder()
                    .success(Boolean.TRUE)
                    .message("Successfully add new movie with id : " + movie.getId())
                    .data(movieResponse)
                    .build();

            return new ResponseEntity<>(webResponse, HttpStatus.CREATED);
        } catch (BadRequestException e) {
            WebResponse<MovieResponse> webResponse = WebResponse.<MovieResponse>builder()
                    .success(Boolean.FALSE)
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(webResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<MovieResponse>> updateMovie(@PathVariable(name = "id") Integer id, @RequestBody UpdateMovieRequest updateMovieRequest) {
        try {
//            validationUtil.validate(updateMovieRequest);
            Movie movie = movieService.updateMovie(id, updateMovieRequest);
            MovieResponse movieResponse = mapFromMovie(movie);
            WebResponse<MovieResponse> webResponse = WebResponse.<MovieResponse>builder()
                    .success(Boolean.TRUE)
                    .message("Successfully update movie with id : " + id)
                    .data(movieResponse)
                    .build();

            return new ResponseEntity<>(webResponse, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            WebResponse<MovieResponse> webResponse = WebResponse.<MovieResponse>builder()
                    .success(Boolean.TRUE)
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(webResponse, HttpStatus.NOT_FOUND);
        } catch (BadRequestException e) {
            WebResponse<MovieResponse> webResponse = WebResponse.<MovieResponse>builder()
                    .success(Boolean.TRUE)
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(webResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<String>> deleteMovie(@PathVariable(name = "id") Integer id) {
        try {
            movieService.deleteMovie(id);
            WebResponse<String> webResponse = WebResponse.<String>builder()
                    .success(Boolean.TRUE)
                    .message("Successfully delete movie with id : " + id)
                    .build();

            return new ResponseEntity<>(webResponse, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            WebResponse<String> webResponse = WebResponse.<String>builder()
                    .success(Boolean.TRUE)
                    .message(e.getMessage())
                    .build();

            return new ResponseEntity<>(webResponse, HttpStatus.NOT_FOUND);
        }
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
