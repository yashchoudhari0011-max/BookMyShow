package com.cfs.bookmyshow;

import com.cfs.bookmyshow.repository.CityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class BookMyShowApplicationTests {

    @Autowired
    private CityRepository cityRepository;

    @Test
    void contextLoads() {
        assertNotNull(cityRepository);
        System.out.println("City count: " + cityRepository.count());
    }
}
