package org.example.data.repositories.goods;

import org.example.data.model.goods.OrderItem;
import org.example.data.model.goods.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
}
