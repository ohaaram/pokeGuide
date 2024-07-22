package com.pokeguide.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "chatMessage")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cmNo;
    private String message;
    private LocalDateTime cDate;
    private int chatNo;
    private String uid;
    private String oName;
    private String sName;
}
