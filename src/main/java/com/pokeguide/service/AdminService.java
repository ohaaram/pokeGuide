package com.pokeguide.service;


import com.pokeguide.dto.UserDTO;
import com.pokeguide.entity.User;
import com.pokeguide.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;


    //유저 리스트 가져오기(관리자 - 회원목록)
    public List<User> userList(){

       List<User> userList =  userRepository.findAll();

       return userList;

    }

    
    //모든 유저 삭제(성공시 1을 반환)
    public int allUserDel(){

        userRepository.deleteAll();

        return 1;
    }

    //유저 권한 변경
    public void changeRole(UserDTO userDTO){

        log.info("서비스안..."+userDTO);

        Optional<User> user = userRepository.findById(userDTO.getUid());

        User chageUser= modelMapper.map(user,User.class);

        chageUser.setRole(userDTO.getRole());

        userRepository.save(chageUser);
    }
}
