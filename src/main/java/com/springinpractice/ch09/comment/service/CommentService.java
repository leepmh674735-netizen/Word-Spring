package com.springinpractice.ch09.comment.service;

import com.springinpractice.ch09.comment.model.Comment;
import com.springinpractice.ch09.article.service.PostCommentCallback;

public interface CommentService {
    void postComment(Comment comment, PostCommentCallback callback);
}
