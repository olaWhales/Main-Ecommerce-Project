package org.example.dto.request.usersRequest;

import lombok.Data;
import org.example.data.model.goods.Address;

import java.time.LocalDate;

@Data
public class DriverRequest {

    private String fullName;
    private String contact ;
    private String email ;
    private LocalDate birthDate ;
    private String password ;
    private String driverLicenseNumber ;
//    private Address address ;
}
