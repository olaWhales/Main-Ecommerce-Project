package org.example.data.model.user;

import jakarta.persistence.*;
import lombok.Data;
import org.example.data.model.goods.Address;
import org.example.data.model.goods.Product;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Seller{
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String contact ;
    private String email ;
    private LocalDate birthDate ;
    private LocalDateTime dateCreated;
    private String password ;
    private String companyName ;
    private String businessAddress ;

    @OneToMany(mappedBy = "seller" , cascade = CascadeType.ALL)
    private List<Product> product;
    @Override
    public String toString() {
        return "Seller{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
