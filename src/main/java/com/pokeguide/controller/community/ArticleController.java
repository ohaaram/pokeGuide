package com.pokeguide.controller.community;


import com.pokeguide.dto.ArticleDTO;
import com.pokeguide.dto.PageRequestDTO;
import com.pokeguide.entity.Article;
import com.pokeguide.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    //글 리스트 출력
    @GetMapping("/community")
    public void allList(@RequestBody PageRequestDTO pageRequestDTO){

        articleService.allList(pageRequestDTO);



    }

    //글 등록
    @PostMapping("/community/register")
    public ResponseEntity<?> register(@RequestBody ArticleDTO articleDTO){

        log.info("여기는 controller의 uid찍어보는 곳 : "+ articleDTO.getUid());

        log.info("여기는 controller의 내용을 찍어보는 곳 : "+ articleDTO.getContents());

        return articleService.register(articleDTO);
    }




}
