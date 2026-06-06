package com.springinpratice.ch08.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.inject.Inject;

@Controller
public class NewsController {
    
    @Inject
    private NewsService newsService;

    @GetMapping("/news.rss")
    public String rss(Model model) {
        model.addAttribute("newsItemList", newsService.getRecentNews());
        return "news";
    }
}