package com.pokeguide.service;


import com.pokeguide.Mapper.UserMapper;
import com.pokeguide.dto.UserDTO;
import com.pokeguide.entity.User;
import com.pokeguide.repository.UserRepository;
import com.pokeguide.util.JWTProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final UserMapper userMapper;
    private final JWTProvider jwtProvider;


    // 이메일 중복 체크
    public boolean isEmailDuplicated(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }

    // 회원가입 //
    public User register(UserDTO userDTO) {
        log.info("Registering user: {}", userDTO);
        if (userDTO.getPass() != null) {
            String encoded = passwordEncoder.encode(userDTO.getPass());
            userDTO.setPass(encoded);
        } else {
            String encoded = passwordEncoder.encode("kakao");
            userDTO.setPass(encoded);
        }

        User user = modelMapper.map(userDTO, User.class);
        User savedUser = userRepository.save(user);

        return savedUser;
    }


    /* 내 설정 수정 */
    public ResponseEntity<?> updateUserInfo(UserDTO userDTO) {

        // 이미지 업로드 처리
        MultipartFile file = userDTO.getFile();
        log.info("파일들어오나 1  ?" + file);

        if (file != null && !file.isEmpty()) {
            String imgPath = uploadImage(file);
            log.info("imgPath 2  : "+imgPath);
            if (imgPath != null) {
                userDTO.setProfile(imgPath);
            }
        }
        int result = 0;
        Optional<User> originUser = userRepository.findById(userDTO.getUid());

        if(userDTO.getPass().equals(originUser.get().getPass())){
            log.info("pass 3 안바꿈");
            result = userMapper.updateUserWithoutPass(userDTO);
        }else {
            // 비밀번호 암호화
            if (userDTO.getPass() != null && !userDTO.getPass().isEmpty()) {
                String encoded = passwordEncoder.encode(userDTO.getPass());
                userDTO.setPass(encoded);
            }
            log.info("pass 바꿈");
            result = userMapper.updateUser(userDTO);
        }

        User updateUser = null;

        if(result > 0){
            log.info("result:"+ result);
            Optional<User> findUser = userRepository.findById(userDTO.getUid());
            if(findUser.isPresent()){
                updateUser = findUser.get();
            }
        }

        UserDTO updateUserDTO = modelMapper.map(updateUser, UserDTO.class);
        log.info("updateUserDTO ?" + updateUserDTO);

        // 토큰 생성
        String access = jwtProvider.createToken(updateUser, 1); // 1일
        String refresh = jwtProvider.createToken(updateUser, 7); // 7일

        log.info("accessToken :" + access);
        log.info("refreshToken :" + refresh);

        // 회원 정보와 토큰을 함께 반환
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("grantType", "Bearer");
        responseMap.put("uid", updateUserDTO.getUid());
        responseMap.put("name", updateUserDTO.getName());
        responseMap.put("nick", updateUserDTO.getNick());
        responseMap.put("role", updateUserDTO.getRole());
        responseMap.put("gender", updateUserDTO.getGender());
        responseMap.put("email", updateUserDTO.getEmail());
        responseMap.put("address", updateUserDTO.getAddress());
        responseMap.put("profile", updateUserDTO.getProfile());
        responseMap.put("accessToken", access);
        responseMap.put("refreshToken", refresh);
        responseMap.put("accessToken", access);
        responseMap.put("refreshToken", refresh);

        return ResponseEntity.status(HttpStatus.OK).body(responseMap);
    }

    /* 이미지 업로드 */
    @Value("${img.upload.path}")
    private String fileUploadPath;
    public String uploadImage(MultipartFile file) {
        String path = new File(fileUploadPath).getAbsolutePath();
        log.info("img:" + file);
        if(!file.isEmpty()){
            String oName = file.getOriginalFilename();
            String ext = oName.substring(oName.lastIndexOf("."));
            String sName = UUID.randomUUID().toString()+ ext;

            try {
                String imgPath = mkMyImg(path, sName, file);

                return imgPath;
            }catch( Exception e){
                log.error("이미지 업로드 오류:" + e.getMessage());
                return null;
            }
        }
        return null;
    }
    /* 이미지 생성 메서드 */
    private String mkMyImg(String path, String sName, MultipartFile file) throws IOException {
        Thumbnails.of(file.getInputStream())
                .size(100,100)
                .toFile(new File(path, "myImg" + sName));
        return "myImg" + sName;
    }

    public Optional<User> findByUid(String uid) {
        return userRepository.findById(uid);
    }

}