package com.springinpractice.ch09.article.service.impl;

import java.util.List;
import java.util.Collections;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.springinpractice.ch09.article.model.Article;
import com.springinpractice.ch09.article.model.ArticlePage;
import com.springinpractice.ch09.comment.model.Comment;
import com.springinpractice.ch09.article.service.ArticleService;

@Service
@Transactional(
        propagation = Propagation.REQUIRED,
        isolation = Isolation.DEFAULT,
        readOnly = true)
public class ArticleServiceImpl implements ArticleService {

    @Override
    public List<Article> getAllArticles() { 
        return Collections.emptyList(); 
    }
    
    @Override
    public ArticlePage getArticlePage(String articleName, int pageNumber) {
        return null;
    }
    
    @Override
    @Transactional(
            propagation = Propagation.REQUIRED,
            isolation = Isolation.DEFAULT,
            readOnly = false)
    public void postComment(final String articleName, Comment comment) {
        // Stub implementation
    }
}
