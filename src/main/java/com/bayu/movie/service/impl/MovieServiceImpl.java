package com.bayu.movie.service.impl;

import com.bayu.movie.dto.CreateMovieRequest;
import com.bayu.movie.dto.MovieResponse;
import com.bayu.movie.dto.UpdateMovieRequest;
import com.bayu.movie.repository.MovieRepository;
import com.bayu.movie.service.MovieService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public MovieResponse addNewMovie(CreateMovieRequest createMovieRequest) {
        return null;
    }

    @Override
    public MovieResponse updateMovie(Integer id, UpdateMovieRequest updateMovieRequest) {
        return null;
    }

    @Override
    public MovieResponse getMovieDetail(Integer id) {
        return null;
    }

    @Override
    public List<MovieResponse> getAllMovies() {
        return null;
    }

    @Override
    public void deleteMovie(Integer id) {

    }
}
