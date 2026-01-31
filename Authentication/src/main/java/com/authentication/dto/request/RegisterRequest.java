package com.authentication.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

public record RegisterRequest (
         @Email @NotBlank
         String email,

         @Size(min = 6)
         String password
){
}
