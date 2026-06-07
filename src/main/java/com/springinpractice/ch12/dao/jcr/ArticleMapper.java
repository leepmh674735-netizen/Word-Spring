package com.springinpractice.ch12.dao.jcr;

import java.sql.Date;
import java.util.Calendar;
import javax.cr.Node;
import javax.cr.RepositoryException;

import org.springframework.stereotype.Component;
import com.springinpractice.ch12.model.Article;
import com.springinpractice.ch12.model.ArticlePage;

@Component
public class ArticleMapper {
    
    public Article toArticle(Node node) throws RepositoryException {
        Article article = new Article();
        article.setId(node.getName());
        article.setTitle(node.getProperty("title").getString());
        article.setAuthor(node.getProperty("author").getString());
        
        if (node.hasProperty("publishDate")) {
            long time = node.getProperty("publishDate").getDate().getTimeInMillis();
            article.setPublishDate(new Date(time));
        }
        if (node.hasProperty("description")) {
            article.setDescription(node.getProperty("description").getString());
        }
        if (node.hasProperty("keywords")) {
            article.setKeywords(node.getProperty("keywords").getString());
        } 
        
        return article;
    }
    
    public Node addArticleNode(Article article, Node parent) throws RepositoryException {
        Node node = parent.addNode(article.getId());
        node.setProperty("title", article.getTitle());
        node.setProperty("author", article.getAuthor());
        
        Date publishDate = article.getPublishDate();
        if (publishDate != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(publishDate);
            node.setProperty("publishDate", cal);
        }
        
        String description = article.getDescription();
        if (description != null) {
            node.setProperty("description", description);
        }
        
        String keywords = article.getKeywords();
        if (keywords != null) {
            node.setProperty("keywords", keywords);
        }
        
        Node pagesNode = node.addNode("pages", "nt:folder");
        int numPages = article.getPages().size();
        for (int i = 0; i < numPages; i++) {
            ArticlePage page = article.getPages().get(i);
            addPageNode(pagesNode, page, i + 1);
        }
        
        return node;
    }
    
    private void addPageNode(Node pagesNode, ArticlePage page, int pageNumber) throws RepositoryException {
        Node pageNode = pagesNode.addNode(String.valueOf(pageNumber), "nt:file");
        Node contentNode = pageNode.addNode(Node.JCR_CONTENT, "nt:resource");
        contentNode.setProperty("jcr:data", page.getContent());
    }
}