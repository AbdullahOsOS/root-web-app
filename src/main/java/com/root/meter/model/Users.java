package com.root.meter.model;


import com.root.meter.DTO.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import javax.transaction.Transactional;
@Transactional
@Entity
@Data
@AllArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "meterId")
    private Meter meter;
    private String name;
    private String email;
    private String password;
    private String state;
    private String phone;
    private String address;

    public Users (UserDTO dto){
        this.setName(dto.getName());
        this.setPassword(dto.getPassword());
        this.setEmail(dto.getEmail());
        this.setPhone(dto.getPhone());
        this.setAddress(dto.getAddress());
        this.setState(dto.getState());
    }
    public Users(){}
}
