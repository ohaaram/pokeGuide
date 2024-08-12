package com.pokeguide.repository.Custom;


import com.pokeguide.dto.PageRequestDTO;
import com.pokeguide.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryCustom {

    // userId 찾기
    public Optional<User> findUserIdByUserNameAndUserEmail(String name, String email);

    // userPw 수정
    public long updateUserPwByUserIdAndUserEmail(String uid,String pass, String email);

    public List<User> searchKeyword(String cate,String keyword);

    public Page<User> allUserList(Pageable pageable);

    public Page<User> searchList(Pageable pageable, PageRequestDTO pageRequestDTO);
}