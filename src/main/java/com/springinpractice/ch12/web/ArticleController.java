package com.springinpractice.ch12.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.springinpractice.ch12.model.Article;
import com.springinpractice.ch12.model.ArticlePage;
import com.springinpractice.ch12.model.dao.ArticleDao;

import jakarta.inject.Inject;

@Controller
@RequestMapping("/articles")
public class ArticleController {
    
    @Inject 
    private ArticleConverter articleConverter;
    
    @Inject 
    private ArticleDao articleDao;
    
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String createArticle(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "redirect:/articles.html?upload=fail"; 
        }
        Article article = articleConverter.convert(file);
        articleDao.create(article);
        return "redirect:/articles.html?upload=ok";
    }
        
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getArticleList(Model model) {
        model.addAttribute("articleList", articleDao.getAll());
        return getFullViewName("articleList");
    }
    
    @RequestMapping(value = "/{id}/{page}", method = RequestMethod.GET)
    public String getArticlePage(
            @PathVariable String id,
            @PathVariable("page") Integer pageNumber,
            Model model) {
        
        Article article = articleDao.getPage(id, pageNumber);
        ArticlePage page = article.getPages().get(pageNumber - 1);
        
        model.addAttribute("article", article);
        model.addAttribute("articlePage", page);
        model.addAttribute("pageNumber", pageNumber);
        
        return getFullViewName("articlePage");
    }
    
    private String getFullViewName(String viewName) {
        return "article/" + viewName;
    }
}