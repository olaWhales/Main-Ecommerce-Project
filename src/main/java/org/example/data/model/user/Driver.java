package org.example.data.model.user;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
public class Driver {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id ;


    private String driverLicenseNumber ;
    private String firstName ;
    private String lastName ;
    private String contact ;
    private String email ;
    private LocalDate birthDate ;
    private LocalDateTime dateCreated;
    private String password ;

}
