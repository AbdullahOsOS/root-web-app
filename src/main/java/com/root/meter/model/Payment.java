package com.root.meter.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private double amount;  //cents
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    private LocalDate paymentDate = LocalDate.of(0001,01,01);

    public Payment() {
    }

    public Payment(double amount, Users userId, LocalDate paymentDate) {
        this.amount = amount;
        this.users = userId;
        this.paymentDate = paymentDate;
    }

}
