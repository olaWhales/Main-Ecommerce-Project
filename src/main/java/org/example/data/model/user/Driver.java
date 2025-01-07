package org.example.data.model.user;

import jakarta.persistence.*;
import lombok.Data;
import org.example.data.model.goods.Address;
import org.example.data.model.logistic.Delivery;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Driver {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String driverLicenseNumber ;
    private String fullName;
    private String contact ;
    private String email ;
    private LocalDate birthDate ;
    private LocalDateTime dateCreated;
    private String password ;

    @OneToMany (mappedBy = "driver" , cascade = CascadeType.ALL , orphanRemoval = true)
    private List<Delivery> delivery = new ArrayList<>();

}
