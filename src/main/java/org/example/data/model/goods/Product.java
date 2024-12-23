package org.example.data.model.goods;

import jakarta.persistence.*;
import lombok.Data;
import org.example.data.model.user.Buyer;
import org.example.data.model.user.Seller;

import java.math.BigDecimal;


@Entity
@Data
public class Product {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;
    private String productDescription;
    private BigDecimal productPrice;
    private Integer productQuantity;

    @ManyToOne
    @JoinColumn (name = "seller_id")
    private Seller seller;

    @ManyToOne
    @JoinColumn(name  = "orderItem_id")
    private OrderItem orderItem;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private Buyer buyer ;
}
