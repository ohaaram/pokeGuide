package com.pokeguide.controller.community;


import com.pokeguide.dto.ArticleDTO;
import com.pokeguide.dto.PageRequestDTO;
import com.pokeguide.entity.Article;
import com.pokeguide.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    //글 리스트 출력
    @PostMapping("/community/list")
    public ResponseEntity<?> allList(@RequestBody PageRequestDTO pageRequestDTO){

        ResponseEntity<?> result = articleService.allList(pageRequestDTO);

        log.info("community-list까지는 들어오는가?");

        return ResponseEntity.status(HttpStatus.OK).body(result);

    }

    //글 등록
    @PostMapping("/community/register")
    public ResponseEntity<?> register(@RequestBody ArticleDTO articleDTO){

        log.info("여기는 controller의 uid찍어보는 곳 : "+ articleDTO.getUid());

        log.info("여기는 controller의 내용을 찍어보는 곳 : "+ articleDTO.getContents());

        return articleService.register(articleDTO);
    }

    @GetMapping("/community/view")
    public ResponseEntity<?> viewer(@RequestParam("ano")String ano){

        log.info("ano가 잘 들어오나 ? : "+ano);

        return articleService.viewer(ano);
    }

}
