package com.cfs.bookmyshow.dto;

import com.cfs.bookmyshow.entity.BookingStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponse {
    private Long bookingId;
    private Long userId;
    private String userName;
    private String movieTitle;
    private String theatreName;
    private String screenName;
    private LocalDate showDate;
    private LocalTime startTime;
    private List<String> seatNumbers;
    private Double totalPrice;
    private BookingStatus status;
    private LocalDateTime bookedAt;
}
