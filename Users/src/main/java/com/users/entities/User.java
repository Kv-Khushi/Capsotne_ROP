package com.users.entities;

import com.users.constant.ConstantMessage;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import com.users.enums.UserRole;

/**
 * Represents a user entity in the system.
 * <p>
 * This class maps to the "users" table in the database and contains details
 * about a user including their phone number, username, email, password, wallet amount, and role.
 * </p>
 */
@Data
@Entity
@Table(name="users")
public class User {

    /**
     * Unique identifier for the user.
     * This is the primary key for the "users" table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    /**
     * The phone number of the user.
     */
    private Long phoneNumber;

    /**
     * The username of the user.
     */
    private String userName;

    /**
     * The email address of the user.
     */
    private String userEmail;

    /**
     * The password of the user.
     */
    private String userPassword;

    /**
     * The wallet amount associated with the user.
     * Initialized with a default value from {@link ConstantMessage#WALLET_AMOUNT}.
     */
//    private Double wallet = ConstantMessage.WALLET_AMOUNT;
    private Double wallet;

    /**
     * The role of the user in the system.
     * This field uses an enum type {@link UserRole}.
     */
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
}
