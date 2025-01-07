package org.example.data.repositories.goods;

import org.example.data.model.goods.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem , Long> {
    List<OrderItem> findAllByIdIn (List<Long> id );
    Optional<OrderItem> findById(Long id);
}

