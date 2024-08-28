package com.restaurants.entities;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long restaurantId;

    private Long userId;

    private String restaurantName;

    private String restaurantAddress;

    private Long contactNumber;

    private String restaurantDescription;

    private Long openingHour;

    private String restaurantImages;

}
