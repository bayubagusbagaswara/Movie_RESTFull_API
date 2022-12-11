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
    @Size(min = 3, message = "Title length minimum must be 3 characters")
    private String title;

    @NotBlank(message = "Description must not be blank")
    @Size(min = 10, max = 500, message = "Description length minimum must be 10 characters and maximum must be 500 characters")
    private String description;

    @NotNull(message = "Rating must not be blank")
    @Min(value = 1, message = "Rating must be greater than or equal to 1")
    private Float rating;

    private String image;
}
