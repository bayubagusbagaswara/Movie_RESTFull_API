package com.bayu.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMovieResponse {

    private String id;

    private String title;

    private String description;

    private String rating;

    private String image;

    private String createdAt;

    private String updatedAt;
}
