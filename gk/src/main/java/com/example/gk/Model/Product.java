package com.example.gk.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String category;
    private double price;
    private String brand;
    private String color;

    public Product() {
    }

    public Product(String name, String category, double price, String brand, String color) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.brand = brand;
        this.color = color;
    }
}
