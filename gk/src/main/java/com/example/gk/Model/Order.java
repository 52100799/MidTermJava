package com.example.gk.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String product_id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private double sumPrice;

    public Order() {
    }

    public Order(String name, String email, String phone, String address, String product_id, double sumPrice) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.product_id = product_id;
        this.sumPrice = sumPrice;
    }
}
