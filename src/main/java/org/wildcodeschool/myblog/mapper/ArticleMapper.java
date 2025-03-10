package org.wildcodeschool.myblog.mapper;

import org.springframework.stereotype.Component;
import org.wildcodeschool.myblog.dto.ArticleCreateDTO;
import org.wildcodeschool.myblog.dto.ArticleDTO;
import org.wildcodeschool.myblog.dto.AuthorDTO;
import org.wildcodeschool.myblog.model.*;

import java.time.LocalDateTime;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ArticleMapper {

    public ArticleDTO convertToDTO(Article article) {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(article.getId());
        articleDTO.setTitle(article.getTitle());
        articleDTO.setContent(article.getContent());
        articleDTO.setUpdatedAt(article.getUpdatedAt());
        if (article.getCategory() != null) {
            articleDTO.setCategoryName(article.getCategory().getName());
        }
        if (article.getImages() != null) {
            articleDTO.setImageUrls(article.getImages().stream().map(Image::getUrl).collect(Collectors.toList()));
        }
        if (article.getArticleAuthors() != null) {
            articleDTO.setAuthorDTOs(article.getArticleAuthors().stream()
                    .filter(articleAuthor -> articleAuthor.getAuthor() != null)
                    .map(articleAuthor -> {
                        AuthorDTO authorDTO = new AuthorDTO();
                        authorDTO.setId(articleAuthor.getAuthor().getId());
                        authorDTO.setFirstname(articleAuthor.getAuthor().getFirstname());
                        authorDTO.setLastname(articleAuthor.getAuthor().getLastname());
                        return authorDTO;
                    })
                    .collect(Collectors.toList()));
        }
        return articleDTO;
    }

    public Article convertToEntity(ArticleCreateDTO articleCreateDTO) {
        Article article = new Article();
        article.setTitle(articleCreateDTO.getTitle());
        article.setContent(articleCreateDTO.getContent());

        if (articleCreateDTO.getCategoryId() != null) {
            Category category = new Category();
            category.setId(articleCreateDTO.getCategoryId());
            article.setCategory(category);
        }

        if (articleCreateDTO.getImages() != null) {
            article.setImages(articleCreateDTO.getImages().stream()
                    .map(imageDTO -> {
                        Image image = new Image();
                        image.setUrl(imageDTO.getUrl());
                        return image;
                    }).collect(Collectors.toList()));
        }

        if (articleCreateDTO.getAuthors() != null) {
            article.setArticleAuthors(articleCreateDTO.getAuthors().stream()
                    .map(authorContributionDTO -> {
                        ArticleAuthor articleAuthor = new ArticleAuthor();
                        Author author = new Author();
                        author.setId(authorContributionDTO.getAuthorId());
                        articleAuthor.setAuthor(author);
                        articleAuthor.setContribution(authorContributionDTO.getContribution());
                        return articleAuthor;
                    }).collect(Collectors.toList()));
        }
        return article;
    }
}