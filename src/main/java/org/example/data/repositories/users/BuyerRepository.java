package org.example.data.repositories.users;

import org.example.data.model.user.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BuyerRepository extends JpaRepository<Buyer, Integer> {
    Optional<Buyer> findByEmailAndPassword(String username , String password);
    Optional<Buyer> findById(Long buyerId);
}
