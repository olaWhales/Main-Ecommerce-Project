package org.example.data.model.goods;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.example.data.model.user.Admin;
import org.example.data.model.user.Buyer;
import org.example.data.model.user.Driver;
import org.example.data.model.user.Seller;

@Entity
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    private String street ;
    private String city ;
    private String houseNumber ;

    @Enumerated
    private UserType userType ;

    @OneToOne
    @JsonIgnore
    private Admin admin ;

    @OneToOne
    @JsonIgnore
    private Buyer buyer ;

    @OneToOne
    @JsonIgnore
    private Driver driver;

    @OneToOne
    @JsonIgnore
    private Seller seller ;

//    @OneToOne(mappedBy = "address" , cascade = CascadeType.ALL)
//    @JsonIgnore
//    private OrderItem orderItem ;
}

