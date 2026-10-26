package com.cfs.bookmyshow.controller;

import com.cfs.bookmyshow.dto.ShowDto;
import com.cfs.bookmyshow.service.ShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/shows")
@RequiredArgsConstructor
public class ShowController {

    private final ShowService showService;

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<ShowDto>> getShowsByMovie(
            @PathVariable Long movieId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) Long cityId) {
        if (cityId != null && date != null) {
            return ResponseEntity.ok(showService.getShowsByMovieAndCity(movieId, cityId, date));
        } else if (date != null) {
            return ResponseEntity.ok(showService.getShowsByMovieAndDate(movieId, date));
        }
        return ResponseEntity.ok(showService.getShowsByMovie(movieId));
    }

    @GetMapping("/{id}/seats")
    public ResponseEntity<Map<String, Object>> getShowSeats(@PathVariable Long id) {
        return ResponseEntity.ok(showService.getShowSeatAvailability(id));
    }

    @PostMapping
    public ResponseEntity<ShowDto> createShow(@RequestBody ShowDto showDto) {
        return new ResponseEntity<>(showService.createShow(showDto), HttpStatus.CREATED);
    }
}
