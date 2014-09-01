package org.summerserver.model.vo;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Cascade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.summerserver.model.dao.CommentDAO;
import org.summerserver.model.dao.StatusUpdateDAO;
import scala.Int;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;

@Entity
@org.hibernate.annotations.Entity(dynamicUpdate = true)
@Table(name = "status_updates")
@JsonAutoDetect
public class StatusUpdate extends Message {

    @JsonIgnore
    protected int likesCount;
    @JsonIgnore
    protected int commentCount;
    @JsonIgnore
    protected int relevance;
    @JsonIgnore
    protected List<Comment> comments;

    @JsonProperty(value = "comments")
    List<Comment> jsonComments;

    public StatusUpdate() {
        super();
        likesCount = commentCount = relevance = 0;
    }

    @Transient
    @JsonProperty
    public HashMap<String, Integer> getCounters() {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.put("likesCount", likesCount);
        map.put("commentsCount", commentCount);
        map.put("relevance", relevance);
        return map;
    }

    @Column(name = "likes_count")
    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
        recalculateRelevance();
    }

    @Column(name = "comments_count")
    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
        recalculateRelevance();
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

    public void removeLike() {
        likesCount--;

        recalculateRelevance();
    }

    private void recalculateRelevance() {
        relevance = likesCount + commentCount * 2;
    }

    public void setJsonComments(List<Comment> jsonComments) {
        this.jsonComments = jsonComments;
    }

    @Transient
    public List<Comment> getJsonComments() {
        return jsonComments;
    }
}
