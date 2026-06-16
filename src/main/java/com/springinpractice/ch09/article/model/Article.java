package com.springinpractice.ch09.article.model;

import java.util.List;
import com.springinpractice.ch09.comment.model.Comment;
import com.springinpractice.ch09.comment.model.CommentTarget;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "article")
public final class Article {
    private Long id;
    private String name;
    private CommentTarget commentTarget;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
