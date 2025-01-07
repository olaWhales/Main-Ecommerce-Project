package org.example.data.model.goods;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.example.data.model.user.Buyer;

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
    private Double totalAmount;

    @ManyToOne
    @JoinColumn(name  = "buyer_id")
    private Buyer buyer ;

    @OneToMany (mappedBy = "orders", cascade = CascadeType.ALL , orphanRemoval = true)
//    @JsonManagedReference
    private List<OrderItem> orderItems = new ArrayList<>();
}
