package com.bayu.movie.dto.mapper;

import com.bayu.movie.dto.MovieResponse;
import com.bayu.movie.model.Movie;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MovieMapper {

    public MovieResponse mapFromMovie(Movie movie) {
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

    public List<MovieResponse> mapFromMovieList(List<Movie> movies) {
        return movies.stream()
                .map(this::mapFromMovie)
                .collect(Collectors.toList());
    }
}
