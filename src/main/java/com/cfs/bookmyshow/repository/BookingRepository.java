package com.cfs.bookmyshow.repository;

import com.cfs.bookmyshow.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUserId(Long userId);

    @Query("SELECT s.id FROM Booking b JOIN b.seats s WHERE b.show.id = :showId AND b.status != com.cfs.bookmyshow.entity.BookingStatus.CANCELLED")
    List<Long> findBookedSeatIdsByShowId(@Param("showId") Long showId);
}
