package com.users.repository;

import com.users.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for {@link User} entities.
 * <p>
 * This interface extends {@link JpaRepository} to provide CRUD operations and custom queries
 * for {@link User} entities. It includes a method to find a user by their email address.
 * </p>
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Retrieves a user by their email address.
     * <p>
     * This method is used to find a {@link User} entity based on the provided email address.
     * </p>
     *
     * @param userEmail the email address of the user to be retrieved
     * @return an {@link Optional} containing the {@link User} if found, or an empty {@link Optional} if not found
     */
        Optional<User> findByUserEmail(String userEmail);
}
