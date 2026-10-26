package com.cfs.bookmyshow.controller;

import com.cfs.bookmyshow.dto.TheatreDto;
import com.cfs.bookmyshow.service.TheatreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/theatres")
@RequiredArgsConstructor
public class TheatreController {

    private final TheatreService theatreService;

    @GetMapping
    public ResponseEntity<List<TheatreDto>> getTheatres(@RequestParam(required = false) Long cityId) {
        if (cityId != null) {
            return ResponseEntity.ok(theatreService.getTheatresByCity(cityId));
        }
        return ResponseEntity.ok(theatreService.getAllTheatres());
    }

    @PostMapping
    public ResponseEntity<TheatreDto> createTheatre(@RequestBody TheatreDto dto) {
        return new ResponseEntity<>(theatreService.createTheatre(dto), HttpStatus.CREATED);
    }
}
