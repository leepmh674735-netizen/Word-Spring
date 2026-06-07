package com.springinpractice.ch12.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Article {
    private String id;
    private String title;
    private String author;
    private Date publishDate;
    private String description;
    private String keywords;
    private List<ArticlePage> pages = new ArrayList<ArticlePage>();
    
    public String getId() { 
        return id; 
    }
    
    public void setId(String id) { 
        this.id = id; 
    }
    
    public String getTitle() { 
        return title; 
    }
    
    public void setTitle(String title) { 
        this.title = title; 
    }
    
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public List<ArticlePage> getPages() {
        return pages;
    }

    public void setPages(List<ArticlePage> pages) {
        this.pages = pages;
    }
}