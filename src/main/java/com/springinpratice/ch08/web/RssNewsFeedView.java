package com.springinpratice.ch08.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.view.feed.AbstractRssFeedView;

// ROME 라이브러리 관련 핵심 클래스 임ポ트 (Spring 내부적으로 사용)
import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Description;
import com.rometools.rome.feed.rss.Item;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// 가정: NewsItem 클래스가 같은 패키지 또는 다른 패키지에 존재함
// import com.springinpratice.ch08.domain.NewsItem;

public class RssNewsFeedView extends AbstractRssFeedView {
    
    private String feedTitle;
    private String feedDesc;
    private String feedLink;

    // Dependency Injection을 위한 Setters
    public void setFeedTitle(String feedTitle) {
        this.feedTitle = feedTitle;
    }

    public void setFeedDesc(String feedDesc) {
        this.feedDesc = feedDesc;
    }

    public void setFeedLink(String feedLink) {
        this.feedLink = feedLink;
    }

    /**
     * 1. 피드의 기본 메타데이터(제목, 설명, 링크)를 설정하는 메서드
     */
    @Override
    protected void buildFeedMetadata(Map<String, Object> model, Channel feed, HttpServletRequest request) {
        feed.setTitle(this.feedTitle != null ? this.feedTitle : "Default Title");
        feed.setDescription(this.feedDesc != null ? this.feedDesc : "Default Description");
        feed.setLink(this.feedLink != null ? this.feedLink : "http://localhost:8080");
    }

    /**
     * 2. 모델 데이터를 RSS 피드 아이템 리스트로 변환하여 리턴하는 메서드
     */
    @Override
    protected List<Item> buildFeedItems(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        
        List<Item> feedItems = new ArrayList<>();
        
        // 기존의 잘못된 변수명(newItems)과 캐스팅 오류 수정
        @SuppressWarnings("unchecked")
        List<NewsItem> newsItems = (List<NewsItem>) model.get("newsItemList");
        
        if (newsItems != null) {
            for (NewsItem newsItem : newsItems) {
                Item feedItem = new Item();
                feedItem.setTitle(newsItem.getTitle());
                feedItem.setAuthor(newsItem.getAuthor()); // newItem -> newsItem 오타 수정
                feedItem.setPubDate(newsItem.getDatePublished());
                feedItem.setLink(newsItem.getLink());
                
                // Description 클래스 오타 및 세팅 수정
                Description desc = new Description(); // Desciption -> Description
                desc.setType("text/html");
                desc.setValue(newsItem.getDescription());
                feedItem.setDescription(desc);
                
                // 리스트에 개별 아이템 추가 (기존엔 자기 자신에게 추가하는 무한 루프 오류였음)
                feedItems.add(feedItem);
            }
        }
        
        // 최종 생성된 RSS 아이템 리스트 리턴 (기존 코드의 returb 오타 수정)
        return feedItems;
    }
}