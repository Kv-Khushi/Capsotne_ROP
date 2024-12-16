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



  /**
   * Checks whether an address exists for a specific user based on the user ID, street, city, state, and zip code.
   * <p>
   * This method is used to verify if a {@link Address} record exists with the specified details.
   * </p>
   *
   * @param userId   the unique identifier of the user
   * @param street   the street of the address
   * @param city     the city of the address
   * @param state    the state of the address
   * @param zipCode  the zip code of the address
   * @return {@code true} if an address exists with the given details, otherwise {@code false}
   */
  boolean existsByUserIdAndStreetAndCityAndStateAndZipCode(
          Long userId,
          String street,
          String city,
          String state,
          Integer zipCode
  );


}
