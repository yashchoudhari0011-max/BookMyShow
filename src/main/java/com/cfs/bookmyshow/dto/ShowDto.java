package com.cfs.bookmyshow.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowDto {
    private Long id;
    private Long movieId;
    private String movieTitle;
    private Long screenId;
    private String screenName;
    private String theatreName;
    private String cityName;
    private LocalDate showDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private Double ticketPrice;
}
