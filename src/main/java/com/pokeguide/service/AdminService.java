package com.pokeguide.service;


import com.pokeguide.dto.PageRequestDTO;
import com.pokeguide.dto.PageResponseDTO;
import com.pokeguide.dto.UserDTO;
import com.pokeguide.entity.User;
import com.pokeguide.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> userList(PageRequestDTO pageRequestDTO){

        Pageable pageable = pageRequestDTO.getPageable("no");

        log.info("어떤게 찍히나? : "+pageable);

        Page<User> userList;

        log.info("키워드 값이 찍히니 ? : "+pageRequestDTO.getKeyword());//확인해볼 차례

        if(pageRequestDTO.getKeyword()==null || pageRequestDTO.getKeyword().isEmpty()) {

           userList = userRepository.allUserList(pageable);

        }else{

            log.info("키워드 값이 있어서 여기로 들어와야해 : ");
           userList = userRepository.searchList(pageable,pageRequestDTO);
        }

       log.info("userList - adminServcie : "+userList);

       if(!userList.getContent().isEmpty()) {
           List<UserDTO> dtoList = userList.getContent().stream().map(entity -> {
               UserDTO dto = modelMapper.map(entity, UserDTO.class);
               return dto;
           }).toList();

           int total = (int) userList.getTotalElements();

           PageResponseDTO<UserDTO> responseDTO = PageResponseDTO.<UserDTO>builder()
                   .pageRequestDTO(pageRequestDTO)
                   .dtoList(dtoList)
                   .total(total)
                   .build();
           return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
       }else{
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
       }
    }

    
    //엑셀용 모든 유저리스트 출력
    public List<User> allUserList(){

        List<User> userList = userRepository.findAll();
        
        return userList;
    }

    
    //모든 유저 삭제(성공시 1을 반환)
    public int allUserDel(){

        userRepository.deleteAll();

        return 1;
    }

    //하나의 유저만 삭제(성공시 1을 반환)
    public int delUser(String uid){//이거 수정해야함. 유저 삭제시, 관련 글 삭제도 해야함

        //userRepository.deleteById(uid);

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



    //유저 상태 활성화로 변경(성공시 1을 반환)
    public int userActive(String uid){//이거 수정해야함. 유저 삭제시, 관련 글 삭제도 해야함

        Optional<User> optUser = userRepository.findById(uid);

        User user = modelMapper.map(optUser, User.class);

        user.setStatus("active");//유저상태 변경

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

    
    //검색
    public List<User> search(String cate, String keyword){

        List<User> users = userRepository.searchKeyword(cate,keyword);

        return users;
        
    }
    
}
