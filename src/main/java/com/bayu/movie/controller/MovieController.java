package com.bayu.movie.controller;

import com.bayu.movie.service.MovieService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    // List of Movie (Get All)

    // Detail of Movie (Get By Id)

    // Add New Movie

    // Update Movie

    // Delete Movie
}
