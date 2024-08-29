package com.pokeguide.repository.Impl;

import com.pokeguide.entity.Article;
import com.pokeguide.entity.QArticle;
import com.pokeguide.entity.QUser;
import com.pokeguide.repository.Custom.ArticleRepositoryCustom;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
@Slf4j
public class ArticleRepositoryImpl implements ArticleRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    private QArticle qArticle = QArticle.article;
    private QUser qUser = QUser.user;

    @Override
    public List<Tuple> allArticleList(Pageable pageable) {

        List<Tuple> result= jpaQueryFactory
                .select(qArticle,qUser.nick)
                .from(qArticle)
                .join(qUser)
                .on(qArticle.uid.eq(qUser.uid))
                .orderBy(qArticle.rdate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        log.info("유저 리스트 - 임플 결과 : "+result.toString());

        long total = result.size();

        log.info("유저 리스트 사이즈 : "+total);

        return result;
    }
}
