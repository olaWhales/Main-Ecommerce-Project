package org.example.data.repositories.users;

import org.example.data.model.user.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Integer> {
    Driver findById(Long driverId);
    Optional<Driver> findByEmail(String email);
}
