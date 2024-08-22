package com.users.entities;
import lombok.Data;
import javax.persistence.*;

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

    private Long wallet=1000L;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

}

