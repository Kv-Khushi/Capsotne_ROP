package com.users.repository;
import com.users.entities.Address;
import com.users.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<User,Long> {
 public List<Address> findByUserId(Long userId);
}
