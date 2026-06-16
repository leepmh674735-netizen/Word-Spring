package com.springinpractice.ch09.article.model;

import java.io.Serializable;

public class ArticlePage implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Article article;
    private int pageNumber;
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
