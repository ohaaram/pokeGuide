package com.pokeguide.dto;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ChatMessageDTO {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cmNo;
    private String message;
    private LocalDateTime cDate;
    private int chatNo;
    private String uid;
    private String oName;
    private String sName;
    private String imageUrl;
}
