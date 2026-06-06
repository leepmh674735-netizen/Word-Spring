package com.springinpratice.ch09.dao;

import org.springframework.stereotype.Repository;

  other imports
@Repository
public class HbnArticleDao extends AbstractHibernateDao<Article> 
    implements ArticleDao {
	..   a couple of finder methods, but nothing else ...

}
