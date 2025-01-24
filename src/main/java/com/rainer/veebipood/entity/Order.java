package com.rainer.veebipood.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "orders") // "Order" on PostgreSQL-s reserveeritud
@SequenceGenerator(name = "seq", initialValue = 1045100, allocationSize = 1)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    private Long id;
    private Date created;

    @ManyToOne
    private Person person;

    private String payment;

    @OneToMany(cascade = CascadeType.ALL) // cascadetype.All => kui @One on alguses
    private List<OrderRow> orderRows;
    private double totalSum;
}
