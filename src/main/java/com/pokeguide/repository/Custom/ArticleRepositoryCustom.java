package com.pokeguide.repository.Custom;

import com.pokeguide.dto.ArticleDTO;
import com.pokeguide.dto.PageRequestDTO;
import com.pokeguide.entity.Article;
import com.querydsl.core.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticleRepositoryCustom {

    public Page<ArticleDTO> allArticleList(Pageable pageable);

    public Page<ArticleDTO> searchList(Pageable pageable,PageRequestDTO pageRequestDTO);

}
