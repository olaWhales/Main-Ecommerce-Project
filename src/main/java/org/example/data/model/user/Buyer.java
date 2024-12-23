package org.example.data.model.user;

import jakarta.persistence.*;
import lombok.Data;
import org.example.data.model.goods.Orders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Buyer {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id ;

    private String address ;
    private String firstName ;
    private String lastName ;
    private String contact ;
    private String email ;
    private LocalDate birthDate ;
    private LocalDateTime dateCreated;
    private String password ;

//    // just add and  i can remove
//    @OneToMany(mappedBy = "buyer")
//    private List<Orders> orders = new ArrayList<>();
}
