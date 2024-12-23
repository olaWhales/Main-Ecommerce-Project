package org.example.data.repositories.goods;

import org.example.data.model.goods.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem , Long> {
    List<OrderItem> findAllByIdIn (List<Long> orderItemIds );
}

