package org.example.data.model.goods;

import jakarta.persistence.*;
import lombok.Data;
import org.example.data.model.user.Buyer;
import org.hibernate.annotations.Fetch;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Orders {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY )
    private Long orderId;

    private LocalDateTime orderDate;
    private BigDecimal totalAmount;

    @ManyToOne
    @JoinColumn(name  = "buyer_id") //i just add this and can be remove
    private Buyer buyer ;

//    @ManyToOne
//    @JoinColumn(name = "product_id")
//    private Product product;

    @OneToMany (mappedBy = "orders", cascade = CascadeType.ALL , orphanRemoval = true)
//    @JoinColumn(name = "orderItem_id")
    private List<OrderItem> orderItems = new ArrayList<>();
}
