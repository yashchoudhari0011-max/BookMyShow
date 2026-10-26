package com.cfs.bookmyshow.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "screens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Screen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer totalSeats;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theatre_id", nullable = false)
    private Theatre theatre;

    @OneToMany(mappedBy = "screen", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    @Builder.Default
    private List<Seat> seats = new ArrayList<>();

    @OneToMany(mappedBy = "screen", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    @Builder.Default
    private List<Show> shows = new ArrayList<>();
}
