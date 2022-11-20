package com.example.backendapp.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrackForUser {
    private Long id;
    private String name;
    private String author_name;
    private String genre_name;
    private Long rate;
}
