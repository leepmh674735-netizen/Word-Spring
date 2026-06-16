package com.springinpractice.ch12.dao.jcr;

import java.io.IOException;
import javax.cr.Session;
import javax.cr.Node;
import javax.cr.RepositoryException;
import javax.cr.PathNotFoundException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.springinpractice.ch12.model.Article;
import com.springinpractice.ch12.model.dao.ArticleDao;

import jakarta.inject.Inject;

@Repository
@Transactional(readOnly = true)
public class JcrArticleDao extends JcrDaoSupport implements ArticleDao {

    @Inject 
    private ArticleMapper articleMapper;
    
    @Transactional(readOnly = false)
    public void create(final Article article) {
        Assert.notNull(article, "Article must not be null");
        getTemplate().execute(new JcrCallback<Object>() {
            @Override
            public Object doInJcr(Session session) throws IOException, RepositoryException {
                if (exists(article.getId())) {
                    throw new DataIntegrityViolationException("Article already exists");
                }
                articleMapper.addArticleNode(article, getArticlesNode(session));
                session.save();
                return null;
            }
        }, true);
    }
    
    public boolean exists(String articleId) {
        try {
            return getTemplate().getSessionFactory().getSession()
            		.nodeExists(getArticlePath(articleId));
        } catch (RepositoryException e) {
            return false;
        }
    }
    
    private String getArticlesNodeName() { 
        return "articles"; 
    }
    
    private String getArticlesPath() { 
        return "/" + getArticlesNodeName(); 
    }
    
    private String getArticlePath(String articleId) {
        return getArticlesPath() + "/" + articleId;
    }
    
    private Node getArticlesNode(Session session) throws RepositoryException {
        try { 
            return session.getNode(getArticlesPath());
        } catch (PathNotFoundException e) {
            return session.getRootNode().addNode(getArticlesNodeName());
        }
    }
}