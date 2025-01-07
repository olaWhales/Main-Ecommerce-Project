package org.example.data.repositories.goods;

import org.example.data.model.goods.Product;
import org.example.data.model.user.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByIdIn(List<Long> productIds);

    Optional<Product> findById(Long sellerId);

    List<Product> findAllBySellerId(Long sellerId);

//        @Query("SELECT p FROM Product p JOIN p.orderItems oi WHERE oi.id = :orderItemId")
        List<Product> findProductsByOrderItemId(@Param("orderItemId") Long orderItemId);

}
