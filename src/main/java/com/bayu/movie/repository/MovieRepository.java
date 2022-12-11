package com.bayu.movie.repository;

import com.bayu.movie.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

    @Query("SELECT m FROM Movies m WHERE m.title = :title")
    Optional<Movie> findByTitle(String title);
}
