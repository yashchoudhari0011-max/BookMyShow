package com.cfs.bookmyshow.repository;

import com.cfs.bookmyshow.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {

    List<Show> findByMovieId(Long movieId);

    List<Show> findByMovieIdAndShowDate(Long movieId, LocalDate showDate);

    @Query("SELECT s FROM Show s WHERE s.movie.id = :movieId AND s.screen.theatre.city.id = :cityId AND s.showDate = :showDate")
    List<Show> findShowsByMovieAndCityAndDate(@Param("movieId") Long movieId,
                                            @Param("cityId") Long cityId,
                                            @Param("showDate") LocalDate showDate);
}
