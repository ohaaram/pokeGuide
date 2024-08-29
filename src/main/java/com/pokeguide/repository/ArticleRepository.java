package com.pokeguide.repository;

import com.pokeguide.entity.Article;
import com.pokeguide.repository.Custom.ArticleRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article,Integer>, ArticleRepositoryCustom {

}
