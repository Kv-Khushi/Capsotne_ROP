package com.users.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Represents an address entity in the system.
 * <p>
 * This class maps to the "Address" table in the database and contains details
 * about a user's address including street, city, state, zip code, country, and associated user ID.
 * </p>
 */
@Data
@Entity
public class Address {

        /**
         * Unique identifier for the address.
         * This is the primary key for the "Address" table.
         */
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long addressId;

        /**
         * The street part of the address.
         */
        private String street;

        /**
         * The city part of the address.
         */
        private String city;

        /**
         * The state part of the address.
         */
        private String state;

        /**
         * The zip code of the address.
         */
        private Integer zipCode;

        /**
         * The country of the address.
         */
        private String country;

        /**
         * The ID of the user associated with this address.
         */
        private Long userId;
}
