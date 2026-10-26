package com.cfs.bookmyshow.controller;

import com.cfs.bookmyshow.entity.City;
import com.cfs.bookmyshow.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
@RequiredArgsConstructor
public class CityController {

    private final CityRepository cityRepository;

    @GetMapping
    public ResponseEntity<List<City>> getAllCities() {
        return ResponseEntity.ok(cityRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<City> createCity(@RequestBody City city) {
        return new ResponseEntity<>(cityRepository.save(city), HttpStatus.CREATED);
    }
}
