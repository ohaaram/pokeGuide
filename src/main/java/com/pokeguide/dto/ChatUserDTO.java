package com.pokeguide.dto;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ChatUserDTO {


    private int cuNo;
    private String uid;
    private int chatNo;
}
