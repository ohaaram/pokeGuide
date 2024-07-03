package com.pokeguide.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User {

    @Id
    private String uid;
    private String name;
    private String pass;
    private String role;
    private String gender;
    private String email;
    private String address;

}
