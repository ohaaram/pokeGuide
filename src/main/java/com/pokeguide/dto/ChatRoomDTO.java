package com.pokeguide.dto;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ChatRoomDTO {

    private int chatNo;
    private String title;
    private String status;

}
