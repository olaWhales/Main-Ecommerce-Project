package org.example.data.repositories.goods;

import org.example.data.model.goods.Product;
import org.example.data.model.user.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findById(Long productId);
//    List<Product> findBySellerId( Long productId);

    List<Product> findBySellerId(Long sellerId);

    List<Product> findAllByIdIn(List<Long> productIds);
}
