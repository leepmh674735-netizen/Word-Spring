package com.springinpractice.ch12.model.dao;

import com.springinpractice.ch12.model.Article;
import com.springinpractice.dao.Dao;

public interface ArticleDao extends Dao<Article> {
	
	void createOrUpdate(Article article);
	
	Article getPage(String articleId, int pageNumber);

}
