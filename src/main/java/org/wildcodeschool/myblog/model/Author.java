package org.wildcodeschool.myblog.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String firstname;

    @Column(nullable = false, length = 50)
    private String lastname;

    @OneToMany(mappedBy = "author")
    private List<ArticleAuthor> articleAuthors;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public List<ArticleAuthor> getArticleAuthors() {
        return articleAuthors;
    }

    public void setArticleAuthors(List<ArticleAuthor> articleAuthors) {
        this.articleAuthors = articleAuthors;
    }
}