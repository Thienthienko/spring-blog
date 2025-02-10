package org.wildcodeschool.myblog.repository;

import org.wildcodeschool.myblog.model.ArticleAuthor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleAuthorRepository extends JpaRepository<ArticleAuthor, Long> {
}