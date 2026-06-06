package com.springinpractice.ch09.article.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springinpratice.ch09.aritcle.model.Article;
import com.springinpratice.ch09.comment.model.Comment;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/articles")
public class ArticleController {
    
    @Inject 
    private ArticleService articleService;
    
    private final String articleListViewName = "articles/list";
    private final String articlePageViewName = "articles/page";
    private final String postCommentFailedViewName = "articles/page";

    @GetMapping("")
    public String getArticles(Model model) {
        model.addAttribute("articleList", articleService.getAllArticles());
        return articleListViewName;
    }

    @GetMapping("/{articleName}/{pageNumber}")
    public String getArticlePage(
            @PathVariable String articleName,
            @PathVariable int pageNumber,
            Model model) {
        
        prepareModel(model, articleName, pageNumber);
        model.addAttribute("comment", new Comment());
        return articlePageViewName;
    }

    @PostMapping("/{articleName}/comments")
    public String postComment(
            HttpServletRequest req, 
            @PathVariable String articleName,
            @RequestParam("p") int pageNumber,
            Model model, 
            @ModelAttribute("comment") @Valid Comment comment,
            BindingResult result) {
        
        if (result.hasErrors()) {
            result.reject("global.error");
            prepareModel(model, articleName, pageNumber);
            return postCommentFailedViewName;
        }
        
        comment.setIpAddress(req.getRemoteAddr());
        articleService.postComment(articleName, comment);
        return "redirect:/articles/" + articleName + "/" + pageNumber + "#comment-" + comment.getId();
    }

    private void prepareModel(Model model, String articleName, int pageNumber) {
        ArticlePage page = articleService.getArticlePage(articleName, pageNumber);
        model.addAttribute("page", page);
        if (page != null && page.getArticle() != null) {
            model.addAttribute("commentList", page.getArticle().getComments());
        }
    }
}