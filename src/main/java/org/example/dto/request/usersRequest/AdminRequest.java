package org.example.dto.request.usersRequest;

import lombok.Data;
import org.example.data.model.goods.Address;

import java.time.LocalDate;

@Data
public class AdminRequest {
    private String fullName;
    private String contact ;
    private String email ;
    private LocalDate birthDate ;
    private String password ;
    private String roles ;
    private String permission ;
//    private Address address ;
}
