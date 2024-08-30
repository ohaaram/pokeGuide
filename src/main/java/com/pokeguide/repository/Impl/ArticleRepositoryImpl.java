package com.pokeguide.repository.Impl;

import com.pokeguide.dto.ArticleDTO;
import com.pokeguide.dto.PageRequestDTO;
import com.pokeguide.entity.Article;
import com.pokeguide.entity.QArticle;
import com.pokeguide.entity.QUser;
import com.pokeguide.repository.Custom.ArticleRepositoryCustom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
@Slf4j
public class ArticleRepositoryImpl implements ArticleRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final ModelMapper modelMapper;


    private QArticle qArticle = QArticle.article;
    private QUser qUser = QUser.user;

    @Override
    //모든 글을 출력(글목록)
    public Page<ArticleDTO> allArticleList(Pageable pageable) {

        List<Tuple> tuples= jpaQueryFactory
                .select(qArticle,qUser.nick)
                .from(qArticle)
                .join(qUser)
                .on(qArticle.uid.eq(qUser.uid))
                .orderBy(qArticle.rdate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        log.info("유저 리스트 - 임플 결과 : "+tuples.toString());

        List<ArticleDTO> result = tuples.stream()
                .map(entity -> {
                    Article article = entity.get(0,Article.class);
                    ArticleDTO dto = modelMapper.map(article, ArticleDTO.class);
                    dto.setNick(entity.get(1,String.class));//닉네임 가져오기
                    return dto;
                }).toList();

        long total = result.size();

        log.info("유저 리스트 사이즈 : "+total);

        return new PageImpl<>(result, pageable, total);
    }

    @Override
    //검색된 글만 출력
    public Page<ArticleDTO> searchList(Pageable pageable, PageRequestDTO pageRequestDTO) {

        BooleanBuilder builder = new BooleanBuilder();

        if(pageRequestDTO.getSearchCate().equals("title")){

            log.info("여기는 제목을 검색했을 떄.....");

            builder.and(qArticle.title.likeIgnoreCase("%" + pageRequestDTO.getKeyword() + "%"));

        }else if(pageRequestDTO.getSearchCate().equals("contents")){

            log.info("여기는 내용을 검색했을 떄.....");

            builder.and(qArticle.contents.likeIgnoreCase("%" + pageRequestDTO.getKeyword() + "%"));

        }else if(pageRequestDTO.getSearchCate().equals("nick")){

            log.info("여기는 닉네임을 검색했을 떄.....");

            builder.and(qUser.nick.likeIgnoreCase("%" + pageRequestDTO.getKeyword() + "%"));

        }else {//title+contents

            log.info("여기는 제목+내용을 검색했을 떄.....");

            builder.and(qArticle.title.likeIgnoreCase("%" + pageRequestDTO.getKeyword() + "%"));
            builder.and(qArticle.contents.likeIgnoreCase("%" + pageRequestDTO.getKeyword() + "%"));

        }


        List<Tuple> tuples= jpaQueryFactory
                .select(qArticle,qUser.nick)
                .from(qArticle)
                .join(qUser)
                .on(qArticle.uid.eq(qUser.uid))
                .where(builder)
                .orderBy(qArticle.rdate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        log.info("유저 리스트 - 임플 결과 : "+tuples.toString());

        List<ArticleDTO> result = tuples.stream()
                .map(entity -> {
                    Article article = entity.get(0,Article.class);
                    ArticleDTO dto = modelMapper.map(article, ArticleDTO.class);
                    dto.setNick(entity.get(1,String.class));//닉네임 가져오기
                    return dto;
                }).toList();

        long total = result.size();

        log.info("유저 리스트 사이즈 : "+total);

        return new PageImpl<>(result, pageable, total);
    }
}
