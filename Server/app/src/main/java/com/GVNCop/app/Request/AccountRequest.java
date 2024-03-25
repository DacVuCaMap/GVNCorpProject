package com.GVNCop.app.Request;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
public class AccountRequest {
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    @NotNull
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;
    @NotNull
    private String name;
    private String avatar;
    @Pattern(regexp = "^[0-9]+$",message = "must be number")
    private String phoneNumber;

}
