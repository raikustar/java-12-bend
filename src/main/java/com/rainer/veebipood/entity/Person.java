package com.rainer.veebipood.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
public class Person {
    @Id
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    @ColumnDefault("false")
    private boolean admin;
}
