package org.summerserver.model.vo;

import javax.persistence.*;
import java.util.List;

@Entity
@org.hibernate.annotations.Entity(dynamicUpdate = true)
@Table(name = "status_updates")
public class StatusUpdate extends Message {

    protected int likesCount;
    protected int commentCount;
    protected int relevance;
    protected List<Comment> comments;

    public StatusUpdate() {
        super();
        likesCount = commentCount = relevance = 0;
    }

    @Column(name = "likes_count")
    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    @Column(name = "comments_count")
    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    @Column(name = "relevance")
    public int getRelevance() {
        return relevance;
    }

    public void setRelevance(int relevance) {
        this.relevance = relevance;
    }

    @OneToMany(mappedBy = "statusUpdate", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(Comment comment) {
        comment.setStatusUpdate(this);
        comments.add(comment);
        commentCount++;

        recalculateRelevance();
    }

    public void addLike() {
        likesCount++;

        recalculateRelevance();
    }

    private void recalculateRelevance() {
        relevance = likesCount + commentCount * 2;
    }
}
