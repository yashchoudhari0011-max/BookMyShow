package com.cfs.bookmyshow.service;

import com.cfs.bookmyshow.dto.MovieDto;
import com.cfs.bookmyshow.entity.Movie;
import com.cfs.bookmyshow.exception.ResourceNotFoundException;
import com.cfs.bookmyshow.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    @Transactional(readOnly = true)
    public List<MovieDto> getAllMovies() {
        return movieRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MovieDto getMovieById(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with id: " + id));
        return mapToDto(movie);
    }

    @Transactional
    public MovieDto createMovie(MovieDto movieDto) {
        Movie movie = Movie.builder()
                .title(movieDto.getTitle())
                .genre(movieDto.getGenre())
                .language(movieDto.getLanguage())
                .durationMinutes(movieDto.getDurationMinutes())
                .rating(movieDto.getRating())
                .releaseDate(movieDto.getReleaseDate())
                .description(movieDto.getDescription())
                .build();

        Movie saved = movieRepository.save(movie);
        return mapToDto(saved);
    }

    @Transactional(readOnly = true)
    public List<MovieDto> searchByTitle(String query) {
        return movieRepository.findByTitleContainingIgnoreCase(query).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MovieDto> getByLanguage(String language) {
        return movieRepository.findByLanguageIgnoreCase(language).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private MovieDto mapToDto(Movie movie) {
        return MovieDto.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .genre(movie.getGenre())
                .language(movie.getLanguage())
                .durationMinutes(movie.getDurationMinutes())
                .rating(movie.getRating())
                .releaseDate(movie.getReleaseDate())
                .description(movie.getDescription())
                .build();
    }
}
