package org.example.dto.request.usersRequest;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AdminRequest {
    private String firstName ;
    private String lastName ;
    private String contact ;
    private String email ;
    private LocalDate birthDate ;
    private LocalDateTime dateCreated;
    private String password ;
    private String roles ;
    private String permission ;
}
