package com.springinpractice.ch09.article.service;

import com.springinpractice.ch09.comment.model.Comment;

public interface PostCommentCallback {
    void post(Comment comment);
}
