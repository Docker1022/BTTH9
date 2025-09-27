package com.example.demo;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private Integer quantity;
    @Column(name = "[desc]")
    private String desc;
    private Double price;

    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;
}