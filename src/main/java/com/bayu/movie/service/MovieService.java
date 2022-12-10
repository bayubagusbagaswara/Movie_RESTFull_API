package com.bayu.movie.service;

import com.bayu.movie.dto.CreateMovieRequest;
import com.bayu.movie.dto.MovieResponse;
import com.bayu.movie.dto.UpdateMovieRequest;

import java.util.List;

public interface MovieService {

    MovieResponse addNewMovie(CreateMovieRequest createMovieRequest);

    MovieResponse updateMovie(Integer id, UpdateMovieRequest updateMovieRequest);

    MovieResponse getMovieDetail(Integer id);

    List<MovieResponse> getAllMovies();

    void deleteMovie(Integer id);
}
