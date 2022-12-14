package com.bayu.movie.service;

import com.bayu.movie.dto.CreateMovieRequest;
import com.bayu.movie.dto.MovieResponse;
import com.bayu.movie.dto.UpdateMovieRequest;
import com.bayu.movie.model.Movie;

import java.util.List;

public interface MovieService {

    Movie addNewMovie(CreateMovieRequest createMovieRequest);

    Movie updateMovie(Integer id, UpdateMovieRequest updateMovieRequest);

    Movie getMovieDetail(Integer id);

    List<Movie> getAllMovies();

    void deleteMovie(Integer id);

    Movie getByTitle(String title);

    List<Movie> getMoviesByTitleContains(String title);
}
