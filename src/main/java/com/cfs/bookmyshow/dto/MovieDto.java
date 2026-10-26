package com.cfs.bookmyshow.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieDto {
    private Long id;
    private String title;
    private String genre;
    private String language;
    private Integer durationMinutes;
    private Double rating;
    private LocalDate releaseDate;
    private String description;
}
