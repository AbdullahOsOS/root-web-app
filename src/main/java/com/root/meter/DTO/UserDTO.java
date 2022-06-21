package com.root.meter.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
@AllArgsConstructor
@Data
public class UserDTO {
    @NotNull
    private Long id;
    private Long meterId;
    @NotNull(message = "username can't be null")
    private String name;
    @NotNull(message = "user's email can't be null")
    private String email;
    @NotNull(message = "password can't be null")
    private String password;
    @NotNull(message = "confirm Password can't be null")
    private String confirmPassword;
    @NotNull(message = "user state can't be null")
    private String state;
    @NotNull(message = "user phone can't be null")
    private String phone;
    @NotNull(message = "user address can't be null")
    private String address;
    @NotNull(message = "user's credit card number can't be null")

    public UserDTO (){}
}