package com.example.backendapp.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A DTO for the {@link com.example.backendapp.Entities.TracksWithLargeAverageRating} entity
 */
@Data
@Getter
@Setter
public class TracksWithLargeAverageRatingDto implements Serializable {
    private final Long trackId;
    private final String trackName;
    private final String genreName;
    private final String authorName;
    private final BigDecimal averageRate;
}