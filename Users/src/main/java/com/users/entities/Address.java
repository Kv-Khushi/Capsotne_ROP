package com.users.entities;

import javax.persistence.*;

@Entity
public class Address {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long addressId;

        private String street;
        private String city;
        private String state;
        private String zipCode;
        private String country;
        private String user_Id;
    }

