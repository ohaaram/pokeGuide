package com.pokeguide.repository;


import com.pokeguide.entity.User;
import com.pokeguide.repository.Custom.UserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String>, UserRepositoryCustom {
    Optional<User> findByEmail(String email); // 중복 가입 확인
    Optional<User> findByHp(String hp);

}
