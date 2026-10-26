package com.cfs.bookmyshow.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shows")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "screen_id", nullable = false)
    private Screen screen;

    @Column(nullable = false)
    private LocalDate showDate;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Column(nullable = false)
    private Double ticketPrice;

    @OneToMany(mappedBy = "show", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    @Builder.Default
    private List<Booking> bookings = new ArrayList<>();
}
