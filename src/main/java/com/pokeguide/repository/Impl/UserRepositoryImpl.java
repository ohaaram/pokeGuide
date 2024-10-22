package com.pokeguide.repository.Impl;


import com.pokeguide.entity.QUser;
import com.pokeguide.entity.User;
import com.pokeguide.repository.Custom.UserRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    private QUser qUser = QUser.user;

    // userID 찾기
    @Override
    public Optional<User> findUserIdByUserNameAndUserEmail(String name, String email) {
        User user = jpaQueryFactory
                .selectFrom(qUser)
                .where(qUser.name.eq(name)
                        .and(qUser.email.eq(email)))
                .fetchOne();
        return Optional.ofNullable(user);
    }

    // UserPw update
    @Override
    @Transactional
    public long updateUserPwByUserIdAndUserEmail(String uid, String pass, String email) {
        try {
            long result = jpaQueryFactory
                    .update(qUser)
                    .set(qUser.pass, pass)
                    .where(qUser.uid.eq(uid)
                            .and(qUser.email.eq(email)))
                    .execute();
            return result;
        } catch (Exception e) {
            log.error("Error msg :" + e.getMessage());
            return -1;
        }
    }

    @Override
    public List<User> searchKeyword(String cate, String keyword) {

        List<User> result = null;

        if(cate.equals("name")) {

            result= jpaQueryFactory
                    .select(qUser)
                    .from(qUser)
                    .where(qUser.name.like(keyword))
                    .fetch();

        }else if(cate.equals("nick")){

            result= jpaQueryFactory
                    .select(qUser)
                    .from(qUser)
                    .where(qUser.nick.like(keyword))
                    .fetch();

        }else if(cate.equals("uid")){

            result= jpaQueryFactory
                    .select(qUser)
                    .from(qUser)
                    .where(qUser.uid.like(keyword))
                    .fetch();

        }

        return  result;
    }

    @Override
    public Page<User> allUserList(Pageable pageable) {

        List<User> result= jpaQueryFactory
                .select(qUser)
                .from(qUser)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        log.info("유저 리스트 - 임플 결과 : "+result.toString());

        long total = result.size();

        log.info("유저 리스트 사이즈 : "+total);

        return new PageImpl<> (result, pageable, total);
    }
}