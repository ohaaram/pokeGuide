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
@Table(name = "alarm")
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String uid;
    private String message;
    private int chatNo;
    private Boolean checked = false;
    private LocalDateTime createdAt = LocalDateTime.now();

}
