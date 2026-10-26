package com.cfs.bookmyshow.service;

import com.cfs.bookmyshow.dto.BookingRequest;
import com.cfs.bookmyshow.dto.BookingResponse;
import com.cfs.bookmyshow.entity.*;
import com.cfs.bookmyshow.exception.ResourceNotFoundException;
import com.cfs.bookmyshow.exception.SeatUnavailableException;
import com.cfs.bookmyshow.repository.BookingRepository;
import com.cfs.bookmyshow.repository.SeatRepository;
import com.cfs.bookmyshow.repository.ShowRepository;
import com.cfs.bookmyshow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ShowRepository showRepository;
    private final SeatRepository seatRepository;

    @Transactional
    public BookingResponse createBooking(BookingRequest request) {
        if (request.getSeatIds() == null || request.getSeatIds().isEmpty()) {
            throw new IllegalArgumentException("At least one seat must be selected for booking");
        }

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.getUserId()));

        Show show = showRepository.findById(request.getShowId())
                .orElseThrow(() -> new ResourceNotFoundException("Show not found with id: " + request.getShowId()));

        List<Seat> selectedSeats = seatRepository.findByIdIn(request.getSeatIds());
        if (selectedSeats.size() != request.getSeatIds().size()) {
            throw new ResourceNotFoundException("One or more seat IDs are invalid");
        }

        // Validate seats belong to the show's screen
        Long screenId = show.getScreen().getId();
        for (Seat seat : selectedSeats) {
            if (!seat.getScreen().getId().equals(screenId)) {
                throw new IllegalArgumentException("Seat " + seat.getSeatNumber() + " does not belong to screen id " + screenId);
            }
        }

        // Check if any seat is already booked for this show
        List<Long> bookedSeatIds = bookingRepository.findBookedSeatIdsByShowId(show.getId());
        Set<Long> bookedSeatIdSet = new HashSet<>(bookedSeatIds);

        for (Seat seat : selectedSeats) {
            if (bookedSeatIdSet.contains(seat.getId())) {
                throw new SeatUnavailableException("Seat " + seat.getSeatNumber() + " is already booked for this show");
            }
        }

        // Calculate total price based on seat type multiplier
        double totalPrice = 0.0;
        for (Seat seat : selectedSeats) {
            double multiplier = 1.0;
            if (seat.getSeatType() == SeatType.GOLD) {
                multiplier = 1.25;
            } else if (seat.getSeatType() == SeatType.PLATINUM) {
                multiplier = 1.5;
            }
            totalPrice += show.getTicketPrice() * multiplier;
        }

        Booking booking = Booking.builder()
                .user(user)
                .show(show)
                .seats(selectedSeats)
                .totalPrice(Math.round(totalPrice * 100.0) / 100.0)
                .status(BookingStatus.CONFIRMED)
                .bookedAt(LocalDateTime.now())
                .build();

        Booking savedBooking = bookingRepository.save(booking);
        return mapToResponse(savedBooking);
    }

    @Transactional(readOnly = true)
    public BookingResponse getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));
        return mapToResponse(booking);
    }

    @Transactional(readOnly = true)
    public List<BookingResponse> getBookingsByUser(Long userId) {
        return bookingRepository.findByUserId(userId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public BookingResponse cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + bookingId));

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new IllegalArgumentException("Booking is already cancelled");
        }

        booking.setStatus(BookingStatus.CANCELLED);
        Booking updatedBooking = bookingRepository.save(booking);
        return mapToResponse(updatedBooking);
    }

    private BookingResponse mapToResponse(Booking booking) {
        return BookingResponse.builder()
                .bookingId(booking.getId())
                .userId(booking.getUser().getId())
                .userName(booking.getUser().getName())
                .movieTitle(booking.getShow().getMovie().getTitle())
                .theatreName(booking.getShow().getScreen().getTheatre().getName())
                .screenName(booking.getShow().getScreen().getName())
                .showDate(booking.getShow().getShowDate())
                .startTime(booking.getShow().getStartTime())
                .seatNumbers(booking.getSeats().stream().map(Seat::getSeatNumber).collect(Collectors.toList()))
                .totalPrice(booking.getTotalPrice())
                .status(booking.getStatus())
                .bookedAt(booking.getBookedAt())
                .build();
    }
}
