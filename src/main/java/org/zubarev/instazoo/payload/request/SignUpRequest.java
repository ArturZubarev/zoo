package org.zubarev.instazoo.payload.request;

import lombok.Data;
import org.zubarev.instazoo.annotations.ValidEmail;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;


@Data
public class SignUpRequest {
    @Email(message = "It should have email format")
    @NotBlank
    @ValidEmail
    private String email;
    @NotEmpty(message = "Please, enter your name")
    private String firstname;
    @NotEmpty(message = "Please, enter your lastname")
    private String lastname;
    @NotEmpty(message ="Please, enter your username")
    private String username;
    @NotEmpty(message = "Please,enter your password")
    @Size(min = 6, message = "Min password length = 6 symbols")
    private String password;
    private String confirmPassword;


}
