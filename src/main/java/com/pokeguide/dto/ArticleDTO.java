package com.pokeguide.dto;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ArticleDTO {

    private int ano;
    private String title;
    private String contents;
    private LocalDateTime rdate;
    private int hit;
    private String uid;
}
