package com.restaurants.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Represents a food category entity in the system.
 */
@Data
@Entity
public class FoodCategory {

    /**
     * The unique identifier for the food category.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    /**
     * The identifier of the restaurant to which this category belongs.
     */
    private Long restaurantId;

    /**
     * The name of the food category.
     */
    private String categoryName;
}
