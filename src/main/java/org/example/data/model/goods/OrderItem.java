package org.example.data.model.goods;

import jakarta.persistence.*;
import lombok.Data;
import org.example.data.model.user.Buyer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Data
@Entity
public class OrderItem {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate deliveryDate ;
    private LocalDateTime orderTime ;
    private Integer quantity ;
    private BigDecimal price ;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus ;

    @ManyToOne
    @JoinColumn(name = "address_id" )
    private Address address ;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private Buyer buyer;

    @ManyToOne
    @JoinColumn (name = "orders_id")
    private Orders orders ;

    @OneToMany(mappedBy = "orderItem", cascade = CascadeType.ALL , orphanRemoval = true)
//    @JoinColumn (name = "orderItem_id")
    private List<Product> products = new ArrayList<>();

}
