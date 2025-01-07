package org.example.data.model.goods;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private Double price ;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address ;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private Buyer buyer;

    @ManyToOne
    @JoinColumn (name = "orders_id")
    private Orders orders ;

    @ManyToMany
    @JoinTable(
            name = "order_item_product",
            joinColumns = @JoinColumn (name = "order_item_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private  List <Product> products  = new ArrayList<>();
}
