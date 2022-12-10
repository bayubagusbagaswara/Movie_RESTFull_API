package com.bayu.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateMovieResponse {

    private Integer id;

    private String title;

    private String description;

    private Float rating;

    private String image;

    private String createdAt;

    private String updatedAt;
}
