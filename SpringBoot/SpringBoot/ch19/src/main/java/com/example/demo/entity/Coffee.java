package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Coffee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "coffee_sq")
    @SequenceGenerator(name="coffee_sq", sequenceName = "coffee_id_sq", initialValue = 1, allocationSize = 1)
    private Long id;
    @Column
    private String name;
    @Column
    private String price;

}
