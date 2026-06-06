package com.springinpratice.ch09.aritcle.model;

import java.util.List;
import com.springinpratice.ch09.comment.model.Comment;
import com.springinpratice.ch09.comment.model.CommentTarget;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "article")
public final class Article {
    
    private CommentTarget commentTarget;
    
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "comment_target_id")
    public CommentTarget getCommentTarget() {
        return commentTarget;
    }
    
    public void setCommentTarget(CommentTarget target) {
        this.commentTarget = target;
    }
    
    @Transient
    public List<Comment> getComments() {
        return (commentTarget != null) ? commentTarget.getComments() : null;
    }
}