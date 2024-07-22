package com.pokeguide.entity;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "chatUser")
public class ChatUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cuNo;
    private String uid;
    private int chatNo;
}
