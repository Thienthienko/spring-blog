package org.wildcodeschool.myblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wildcodeschool.myblog.model.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}