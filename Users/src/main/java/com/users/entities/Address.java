package com.users.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Data
@Entity
public class Address {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long addressId;

        private String street;
        private String city;
        private String state;
        private Integer zipCode;
        private String country;
        private Long userId;


}

