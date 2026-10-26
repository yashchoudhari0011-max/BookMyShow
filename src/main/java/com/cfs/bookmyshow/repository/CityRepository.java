package com.cfs.bookmyshow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cfs.bookmyshow.entity.City;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    Optional<City> findByNameIgnoreCase(String name);
}
