package com.springinpratice.ch08.service.impl;

import java.util.Date;
import org.springframework.stereotype.Service;
import com.springinpratice.ch09.comment.model.Comment;
import jakarta.inject.Inject;

@Service
public class CommentServiceImpl implements CommentService {
    @Inject private TextFilter textFilter;
    @Inject private CommentMailSender mailSender;
    
    public TextFilter getTextFilter() { 
        return textFilter; 
    }

    public void setTextFilter(TextFilter filter) {
        this.textFilter = filter;
    }
    
    public void postComment(final Comment comment, final PostCommentCallback callback) {
        prepareComment(comment);
        callback.post(comment);
        mailSender.sendNotificationEmail(comment);
    }
    
    private void prepareComment(final Comment comment) {
        comment.setWeb(WebUtils.cleanupWebUrl(comment.getWeb()));
        comment.setDateCreated(new Date());
        comment.setText(textFilter.filter(comment.getText()));
    }
}