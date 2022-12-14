package com.bayu.movie.repository;

import com.bayu.movie.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

    Optional<Movie> findByTitleIgnoreCase(String title);

    List<Movie> findByTitleContainsIgnoreCase(String title);
}
