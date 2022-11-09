package com.example.backendapp.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrackDto implements Serializable {
    private Long id;
    private String name;
    private String genreName;
    private String authorName;
    private Integer rate;
    private Long trackFileId;
}