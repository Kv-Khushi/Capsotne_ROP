package com.users.repository;

import com.users.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for {@link Address} entities.
 * <p>
 * This interface extends {@link JpaRepository} and provides methods to perform CRUD operations
 * on {@link Address} entities. It also includes a custom method to find addresses based on the user ID.
 * </p>
 */
public interface AddressRepository extends JpaRepository<Address, Long> {

  /**
   * Retrieves a list of addresses associated with a specific user ID.
   * <p>
   * This method is used to find all {@link Address} records that are linked to the provided user ID.
   * </p>
   *
   * @param userId the unique identifier of the user whose addresses are to be retrieved
   * @return a list of {@link Address} entities associated with the given user ID
   */
  List<Address> findByUserId(Long userId);

  boolean existsByUserIdAndStreetAndCityAndStateAndZipCode(
          Long userId,
          String street,
          String city,
          String state,
          Integer zipCode
  );
}
