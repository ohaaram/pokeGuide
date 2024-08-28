package com.pokeguide.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "article")
public class Article {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int ano;
    private String title;
    private String contents;

    @CreationTimestamp
    private LocalDateTime rdate;
    private int hit;
    private String uid;
}
