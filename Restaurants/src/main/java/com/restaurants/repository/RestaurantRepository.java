package com.restaurants.repository;

import com.restaurants.entities.Restaurant;

import org.springframework.data.jpa.repository.JpaRepository;


public interface RestaurantRepository extends JpaRepository<Restaurant, String> {


}
