package com.bayu.movie.service.impl;

import com.bayu.movie.dto.CreateMovieRequest;
import com.bayu.movie.dto.UpdateMovieRequest;
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

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public Movie addNewMovie(CreateMovieRequest createMovieRequest) {
        Movie movie = Movie.builder()
                .title(createMovieRequest.getTitle())
                .description(createMovieRequest.getDescription())
                .rating(createMovieRequest.getRating())
                .image(createMovieRequest.getImage())
                .createdAt(LocalDateTime.now())
                .build();

        movieRepository.save(movie);

        return movie;
    }

    @Override
    public Movie updateMovie(Integer id, UpdateMovieRequest updateMovieRequest) {
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

        return movie;
    }

    @Override
    public Movie getMovieDetail(Integer id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with id : " + id));
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public void deleteMovie(Integer id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with id : " + id));
        movieRepository.delete(movie);
    }

}
