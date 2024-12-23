package org.example.data.model.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@Entity
public class Seller{
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id ;

    private String firstName ;
    private String lastName ;
    private String contact ;
    private String email ;
    private LocalDate birthDate ;
    private LocalDateTime dateCreated;
    private String password ;
    private String companyName ;
    private String businessAddress ;
}
