package com.cfs.bookmyshow.controller;

import com.cfs.bookmyshow.dto.MovieDto;
import com.cfs.bookmyshow.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    public ResponseEntity<List<MovieDto>> getAllMovies(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String language) {
        if (search != null && !search.isBlank()) {
            return ResponseEntity.ok(movieService.searchByTitle(search));
        }
        if (language != null && !language.isBlank()) {
            return ResponseEntity.ok(movieService.getByLanguage(language));
        }
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDto> getMovieById(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.getMovieById(id));
    }

    @PostMapping
    public ResponseEntity<MovieDto> createMovie(@RequestBody MovieDto movieDto) {
        return new ResponseEntity<>(movieService.createMovie(movieDto), HttpStatus.CREATED);
    }
}
