package org.summerserver.model.vo;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity
@org.hibernate.annotations.Entity(dynamicUpdate = true)
@Inheritance(strategy = InheritanceType.JOINED)
@JsonAutoDetect
public abstract class Message {

    @JsonProperty
    protected Long id;
    @JsonProperty
    protected Date date;

    @Embedded
    @JsonProperty
    protected Author author;

    @JsonProperty
    protected String message;

    public Message() {
        date = new Date();
    }

    @Id
    @Column(name = "id", length = 255)
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    @java.beans.Transient
    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Column(name = "message")
    @NotNull
    @Pattern(regexp = "/^\\.$/")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
