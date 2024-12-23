package org.example.dto.request.usersRequest;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class BuyerRequest {
    private String firstName ;
    private String lastName ;
    private String contact ;
    private String email ;
    private LocalDate birthDate ;
    private LocalDateTime dateCreated;
    private String password ;
    private String Address ;
    private String phoneNumber ;
}
