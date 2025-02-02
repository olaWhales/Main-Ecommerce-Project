package org.example.data.repositories.users;

import org.example.data.model.user.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
    Optional<Seller> findByEmail(String username);

    Optional<Object> findByEmailAndPassword(String email, String password);
}
