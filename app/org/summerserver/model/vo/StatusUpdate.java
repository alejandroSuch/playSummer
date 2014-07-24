package org.summerserver.model.vo;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "status_updates")
public class StatusUpdate extends Message {
    @Column
    protected int likesCount;

    @Column
    protected int commentCount;

    @Column
    protected int relevance;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "statusUpdate")
    protected List<Comment> comments;

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getRelevance() {
        return relevance;
    }

    public void setRelevance(int relevance) {
        this.relevance = relevance;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
