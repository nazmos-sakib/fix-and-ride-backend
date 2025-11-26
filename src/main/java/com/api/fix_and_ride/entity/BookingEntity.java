package com.api.fix_and_ride.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "bookings")
public class BookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private UserEntity user;

    @ManyToOne(optional = false)
    private ServiceItemEntity service;

    private LocalDate date;

    @Column(nullable = false)
    private String status; // PENDING, ACCEPTED, DONE

    public BookingEntity() {}

    public BookingEntity(UserEntity user, ServiceItemEntity service, LocalDate date, String status) {
        this.user = user;
        this.service = service;
        this.date = date;
        this.status = status;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public ServiceItemEntity getService() {
        return service;
    }

    public void setService(ServiceItemEntity service) {
        this.service = service;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
