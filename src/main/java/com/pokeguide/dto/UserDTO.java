package com.pokeguide.dto;


import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserDTO {

    private String uid;
    private String name;
    private String pass;
    private String nick;
    private String hp;
    private String role;
    private String gender;
    private String email;
    private String address;
    private String profile;
    private String refreshToken;
    private String createDate;
    private int visitCount;
    private String status;



    // 파일 업로드 추가 //
    private MultipartFile file;

}
