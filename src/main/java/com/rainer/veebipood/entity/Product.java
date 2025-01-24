package com.rainer.veebipood.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
public class Product {
        @Id
        private String name;
        private double price;
        private boolean active;
        private int stock;

        @ManyToOne Category category;

        // 4 valikut kuidas seoseid teha
        // @ManyToMany --> paremal peab olema list
        // @OneToMany --> paremal peab olema list
        // @ManyToOne --> vasaul pool many ja see tähendab, et jagatakse seda tabelit
        //                (teisel tootel võib ka sama võti olla)

        // @OneToOne --> vasakul on One ja kui keegi teine võtab sama võõrvõtme siin kasutusele, siis läheb errorisse


        //
        @OneToOne(cascade = CascadeType.ALL) // Kõik liikumised, suhtlus läbi Producti
        private Nutrients nutrients;

    }
