package com.bayu.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateMovieRequest {

    @NotBlank(message = "Title must not be blank")
    private String title;

    @Size(min = 10, max = 500, message = "Description length minimum must be 10 characters and maximum must be 500 characters")
    private String description;

    @Min(value = 1, message = "Rating minimum is 1")
    @Max(value = 10, message = "Rating maximum is 10")
    private Float rating;

    private String image;
}
