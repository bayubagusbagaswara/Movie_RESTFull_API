package com.bayu.movie.service.impl;

import com.bayu.movie.dto.CreateMovieRequest;
import com.bayu.movie.dto.MovieResponse;
import com.bayu.movie.dto.UpdateMovieRequest;
import com.bayu.movie.dto.mapper.MovieMapper;
import com.bayu.movie.exception.ResourceNotFoundException;
import com.bayu.movie.model.Movie;
import com.bayu.movie.repository.MovieRepository;
import com.bayu.movie.service.MovieService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    private final MovieMapper movieMapper;

    public MovieServiceImpl(MovieRepository movieRepository, MovieMapper movieMapper) {
        this.movieRepository = movieRepository;
        this.movieMapper = movieMapper;
    }

    @Override
    public MovieResponse addNewMovie(CreateMovieRequest createMovieRequest) {
        Movie movie = Movie.builder()
                .title(createMovieRequest.getTitle())
                .description(createMovieRequest.getDescription())
                .rating(createMovieRequest.getRating())
                .image(createMovieRequest.getImage())
                .createdAt(LocalDateTime.now())
                .build();

        movieRepository.save(movie);

        return movieMapper.mapFromMovie(movie);
    }

    @Override
    public MovieResponse updateMovie(Integer id, UpdateMovieRequest updateMovieRequest) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with id : " + id));

        if (updateMovieRequest.getTitle() != null) {
            movie.setTitle(updateMovieRequest.getTitle());
        }

        if (updateMovieRequest.getDescription() != null) {
            movie.setDescription(updateMovieRequest.getDescription());
        }

        if (updateMovieRequest.getRating() != null) {
            movie.setRating(updateMovieRequest.getRating());
        }

        if (updateMovieRequest.getImage() != null) {
            movie.setImage(updateMovieRequest.getImage());
        }

        movie.setUpdatedAt(LocalDateTime.now());

        movieRepository.save(movie);

        return movieMapper.mapFromMovie(movie);
    }

    @Override
    public MovieResponse getMovieDetail(Integer id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with id : " + id));

        return movieMapper.mapFromMovie(movie);
    }

    @Override
    public List<MovieResponse> getAllMovies() {
        List<Movie> movieList = movieRepository.findAll();
        return movieMapper.mapFromMovieList(movieList);
    }

    @Override
    public void deleteMovie(Integer id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with id : " + id));

        movieRepository.delete(movie);
    }

}
