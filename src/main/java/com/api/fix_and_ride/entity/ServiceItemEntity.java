package com.api.fix_and_ride.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "services")
public class ServiceItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String keyName;   // "car-labor", "labor-only", etc.

    @Column(nullable = false)
    private String name;

    private double price;
    @Column(length = 1000)
    private String description;

    public ServiceItemEntity() {}

    public ServiceItemEntity(String keyName, String name, String description) {
        this.keyName = keyName;
        this.name = name;
        this.description = description;
    }

}
