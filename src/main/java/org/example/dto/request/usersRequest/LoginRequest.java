package org.example.dto.request.usersRequest;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
