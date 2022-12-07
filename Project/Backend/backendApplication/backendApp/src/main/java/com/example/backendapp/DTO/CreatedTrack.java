package com.example.backendapp.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatedTrack {
    private Long id;
    private String name;
    private String genreId;
    private String authorId;
    private Long trackFileId;
}
