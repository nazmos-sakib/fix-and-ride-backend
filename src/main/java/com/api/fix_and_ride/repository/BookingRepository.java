package com.api.fix_and_ride.repository;

import com.api.fix_and_ride.entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<BookingEntity, Long> {

    // User can see only their own bookings
    List<BookingEntity> findByUser_Email(String email);}
