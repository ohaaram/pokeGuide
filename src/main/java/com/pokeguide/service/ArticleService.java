package com.pokeguide.service;


import com.pokeguide.dto.ArticleDTO;
import com.pokeguide.dto.PageRequestDTO;
import com.pokeguide.dto.PageResponseDTO;
import com.pokeguide.dto.UserDTO;
import com.pokeguide.entity.Article;
import com.pokeguide.entity.User;
import com.pokeguide.repository.ArticleRepository;
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

@Slf4j
@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ModelMapper modelMapper;
    private final ArticleRepository articleRepository;

    //글 리스트 출력
    public ResponseEntity<?> allList(PageRequestDTO pageRequestDTO){

        Pageable pageable = pageRequestDTO.getPageable("no");
        Page<Article> articleList=null;//여기에 null제거해주기!

        log.info("키워드 값이 찍히니 ? : "+pageRequestDTO.getKeyword());//확인해볼 차례

        if(pageRequestDTO.getKeyword()==null || pageRequestDTO.getKeyword().isEmpty()) {

            //articleList = articleRepository.allUserList(pageable);

        }else{

            log.info("키워드 값이 있어서 여기로 들어와야해 : ");
            //articleList = articleRepository.searchList(pageable,pageRequestDTO);
        }

        //log.info("articleList - adminServcie : "+articleList);

        if(!articleList.getContent().isEmpty()) {
            List<UserDTO> dtoList = articleList.getContent().stream().map(entity -> {
                UserDTO dto = modelMapper.map(entity, UserDTO.class);
                return dto;
            }).toList();

            int total = (int) articleList.getTotalElements();

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

    //글쓰기
    public ResponseEntity<?> register(ArticleDTO articleDTO){

        if(articleDTO!=null){

            Article article = modelMapper.map(articleDTO, Article.class);

            articleRepository.save(article);

            return ResponseEntity.status(HttpStatus.OK).body(1);

        }else{
            return ResponseEntity.status(HttpStatus.OK).body(0);
        }
    }
}
