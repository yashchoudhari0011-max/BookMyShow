package com.cfs.bookmyshow.service;

import com.cfs.bookmyshow.dto.TheatreDto;
import com.cfs.bookmyshow.entity.City;
import com.cfs.bookmyshow.entity.Theatre;
import com.cfs.bookmyshow.exception.ResourceNotFoundException;
import com.cfs.bookmyshow.repository.CityRepository;
import com.cfs.bookmyshow.repository.TheatreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TheatreService {

    private final TheatreRepository theatreRepository;
    private final CityRepository cityRepository;

    @Transactional(readOnly = true)
    public List<TheatreDto> getAllTheatres() {
        return theatreRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TheatreDto> getTheatresByCity(Long cityId) {
        return theatreRepository.findByCityId(cityId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public TheatreDto createTheatre(TheatreDto dto) {
        City city = cityRepository.findById(dto.getCityId())
                .orElseThrow(() -> new ResourceNotFoundException("City not found with id: " + dto.getCityId()));

        Theatre theatre = Theatre.builder()
                .name(dto.getName())
                .address(dto.getAddress())
                .city(city)
                .build();

        Theatre saved = theatreRepository.save(theatre);
        return mapToDto(saved);
    }

    private TheatreDto mapToDto(Theatre theatre) {
        return TheatreDto.builder()
                .id(theatre.getId())
                .name(theatre.getName())
                .address(theatre.getAddress())
                .cityId(theatre.getCity().getId())
                .cityName(theatre.getCity().getName())
                .build();
    }
}
