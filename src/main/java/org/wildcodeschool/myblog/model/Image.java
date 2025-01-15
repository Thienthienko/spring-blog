package org.wildcodeschool.myblog.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String url;

    @ManyToMany(mappedBy = "images")
    private List<Article> articles;

    // Getter pour id
    public Long getId() {
        return id;
    }

    // Setter pour id
    public void setId(Long id) {
        this.id = id;
    }

    // Getter pour url
    public String getUrl() {
        return url;
    }

    // Setter pour url
    public void setUrl(String url) {
        this.url = url;
    }

    // Getter pour articles
    public List<Article> getArticles() {
        return articles;
    }

    // Setter pour articles
    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
