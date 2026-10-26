package com.cfs.bookmyshow.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TheatreDto {
    private Long id;
    private String name;
    private String address;
    private Long cityId;
    private String cityName;
}
