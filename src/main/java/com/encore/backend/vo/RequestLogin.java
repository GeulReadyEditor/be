package com.encore.backend.vo;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class RequestLogin {
    @NotNull(message = "Email cannot be null")
    @Size(min = 2, message = "Email must be at least 2 characters")
    @Email
    private String email;

    @NotNull(message = "Email cannot be null")
    @Size(min = 8, message = "password must be equals or greater than 8 characters")
    private String password;
}
