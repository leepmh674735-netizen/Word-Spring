package com.springinpratice.ch09.article.service.impl;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.springinpratice.ch09.aritcle.model.Article;
import com.springinpratice.ch09.comment.model.Comment;

import jakarta.inject.Inject;

@Service
@Transactional(
        propagation = Propagation.REQUIRED,
        isolation = Isolation.DEFAULT,
        readOnly = true)
public class ArticleServiceImpl implements ArticleService {
    
    @Inject private ArticleDao articleDao;
    @Inject private ArticlePageDao pageDao;
    @Inject private CommentService commentService;
    
    public List<Article> getAllArticles() { 
        return articleDao.getAll(); 
    }
    
    public ArticlePage getArticlePage(String articleName, int pageNumber) {
        ArticlePage page = pageDao.getByArticleNameAndPageNumber(articleName, pageNumber);
        if (page != null && page.getArticle() != null) {
            Hibernate.initialize(page.getArticle().getComments());
        }
        return page;
    }
    
    @Transactional(
            propagation = Propagation.REQUIRED,
            isolation = Isolation.DEFAULT,
            readOnly = false)
    public void postComment(final String articleName, Comment comment) {
        commentService.postComment(comment, new PostCommentCallback() {
            @Override
            public void post(Comment comment) {
                Article article = articleDao.getByName(articleName);
                if (article != null) {
                    article.getComments().add(comment);
                    articleDao.update(article);
                }
            }
        });
    }
}