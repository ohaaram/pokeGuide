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

    //하나의 유저만 삭제(성공시 1을 반환)
    public int delUser(String uid){//이거 수정해야함. 유저 삭제시, 관련 글 삭제도 해야함

        userRepository.deleteById(uid);

        return 1;

    }


    //유저 상태 정지로 변경(성공시 1을 반환)
    public int userStop(String uid){//이거 수정해야함. 유저 삭제시, 관련 글 삭제도 해야함

        Optional<User> optUser = userRepository.findById(uid);

        User user = modelMapper.map(optUser, User.class);

        user.setStatus("inactive");//유저상태 변경

        userRepository.save(user);

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
