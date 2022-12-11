package com.bayu.movie.controller;

import com.bayu.movie.dto.*;
import com.bayu.movie.exception.AppException;
import com.bayu.movie.exception.BadRequestException;
import com.bayu.movie.exception.ResourceNotFoundException;
import com.bayu.movie.service.MovieService;
import com.bayu.movie.util.ValidationUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<WebResponse<List<MovieResponse>>> listOfMovie() {
        try {
            List<MovieResponse> movies = movieService.getAllMovies();
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
            MovieResponse movieDetail = movieService.getMovieDetail(id);
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
    public ResponseEntity<WebResponse<MovieResponse>> addNewMovie(@RequestBody CreateMovieRequest createMovieRequest) {
        try {
            validationUtil.validate(createMovieRequest);
            MovieResponse movie = movieService.addNewMovie(createMovieRequest);
            WebResponse<MovieResponse> webResponse = WebResponse.<MovieResponse>builder()
                    .success(Boolean.TRUE)
                    .message("Successfully add new movie with id : " + movie.getId())
                    .data(movie)
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
            validationUtil.validate(updateMovieRequest);
            MovieResponse movie = movieService.updateMovie(id, updateMovieRequest);
            WebResponse<MovieResponse> webResponse = WebResponse.<MovieResponse>builder()
                    .success(Boolean.TRUE)
                    .message("Successfully update movie with id : " + id)
                    .data(movie)
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

}
