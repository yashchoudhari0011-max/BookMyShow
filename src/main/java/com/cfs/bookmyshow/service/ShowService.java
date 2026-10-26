package com.cfs.bookmyshow.service;

import com.cfs.bookmyshow.dto.ShowDto;
import com.cfs.bookmyshow.entity.Movie;
import com.cfs.bookmyshow.entity.Screen;
import com.cfs.bookmyshow.entity.Seat;
import com.cfs.bookmyshow.entity.Show;
import com.cfs.bookmyshow.exception.ResourceNotFoundException;
import com.cfs.bookmyshow.repository.BookingRepository;
import com.cfs.bookmyshow.repository.MovieRepository;
import com.cfs.bookmyshow.repository.ScreenRepository;
import com.cfs.bookmyshow.repository.SeatRepository;
import com.cfs.bookmyshow.repository.ShowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShowService {

    private final ShowRepository showRepository;
    private final MovieRepository movieRepository;
    private final ScreenRepository screenRepository;
    private final SeatRepository seatRepository;
    private final BookingRepository bookingRepository;

    @Transactional(readOnly = true)
    public List<ShowDto> getShowsByMovie(Long movieId) {
        return showRepository.findByMovieId(movieId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ShowDto> getShowsByMovieAndDate(Long movieId, LocalDate date) {
        return showRepository.findByMovieIdAndShowDate(movieId, date).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ShowDto> getShowsByMovieAndCity(Long movieId, Long cityId, LocalDate date) {
        return showRepository.findShowsByMovieAndCityAndDate(movieId, cityId, date).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ShowDto createShow(ShowDto dto) {
        Movie movie = movieRepository.findById(dto.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with id: " + dto.getMovieId()));
        Screen screen = screenRepository.findById(dto.getScreenId())
                .orElseThrow(() -> new ResourceNotFoundException("Screen not found with id: " + dto.getScreenId()));

        Show show = Show.builder()
                .movie(movie)
                .screen(screen)
                .showDate(dto.getShowDate())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .ticketPrice(dto.getTicketPrice())
                .build();

        Show saved = showRepository.save(show);
        return mapToDto(saved);
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getShowSeatAvailability(Long showId) {
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new ResourceNotFoundException("Show not found with id: " + showId));

        List<Seat> allSeats = seatRepository.findByScreenId(show.getScreen().getId());
        List<Long> bookedSeatIds = bookingRepository.findBookedSeatIdsByShowId(showId);
        Set<Long> bookedSeatIdSet = new HashSet<>(bookedSeatIds);

        List<Map<String, Object>> seatAvailabilityList = allSeats.stream().map(seat -> {
            Map<String, Object> seatMap = new HashMap<>();
            seatMap.put("seatId", seat.getId());
            seatMap.put("seatNumber", seat.getSeatNumber());
            seatMap.put("row", seat.getRow());
            seatMap.put("col", seat.getCol());
            seatMap.put("seatType", seat.getSeatType());
            seatMap.put("isAvailable", !bookedSeatIdSet.contains(seat.getId()));
            return seatMap;
        }).collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("show", mapToDto(show));
        response.put("totalSeats", allSeats.size());
        response.put("availableSeatsCount", allSeats.size() - bookedSeatIdSet.size());
        response.put("seats", seatAvailabilityList);
        return response;
    }

    public ShowDto mapToDto(Show show) {
        return ShowDto.builder()
                .id(show.getId())
                .movieId(show.getMovie().getId())
                .movieTitle(show.getMovie().getTitle())
                .screenId(show.getScreen().getId())
                .screenName(show.getScreen().getName())
                .theatreName(show.getScreen().getTheatre().getName())
                .cityName(show.getScreen().getTheatre().getCity().getName())
                .showDate(show.getShowDate())
                .startTime(show.getStartTime())
                .endTime(show.getEndTime())
                .ticketPrice(show.getTicketPrice())
                .build();
    }
}
