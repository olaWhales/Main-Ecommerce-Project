package org.example.data.model.logistic;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Vehicle {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id ;
    private String licensePlate ;
    private String model ;

   @OneToMany (mappedBy = "vehicle" , cascade = CascadeType.ALL , orphanRemoval = true)
   private List<Delivery> delivery = new ArrayList<>();
}
