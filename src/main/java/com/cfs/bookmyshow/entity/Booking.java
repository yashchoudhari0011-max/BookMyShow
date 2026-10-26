package com.cfs.bookmyshow.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "show_id", nullable = false)
    private Show show;

    @Column(nullable = false)
    private Double totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status;

    @Column(nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime bookedAt = LocalDateTime.now();

    @ManyToMany
    @JoinTable(
        name = "booking_seats",
        joinColumns = @JoinColumn(name = "booking_id"),
        inverseJoinColumns = @JoinColumn(name = "seat_id")
    )
    @Builder.Default
    private List<Seat> seats = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        if (this.bookedAt == null) {
            this.bookedAt = LocalDateTime.now();
        }
        if (this.status == null) {
            this.status = BookingStatus.CONFIRMED;
        }
    }
}
