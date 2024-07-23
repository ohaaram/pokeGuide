package com.pokeguide.service;


import com.pokeguide.entity.User;
import com.pokeguide.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

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


}
