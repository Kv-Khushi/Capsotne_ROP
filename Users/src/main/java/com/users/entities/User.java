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


@Data
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private Long phoneNumber;
    private String userName;
    private String userEmail;
    private String userPassword;
    private Long wallet= ConstantMessage.WALLET_AMOUNT;


    @Enumerated(EnumType.STRING)
    private UserRole userRole;

}

