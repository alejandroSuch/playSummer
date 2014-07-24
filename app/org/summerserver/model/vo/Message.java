package org.summerserver.model.vo;

import javax.persistence.*;
import java.beans.*;
import java.util.Date;
import java.util.UUID;

@Entity
@org.hibernate.annotations.Entity(dynamicUpdate = true)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Message {

    protected String id;
    protected Date date;

    @Embedded
    protected Author author;
    protected String message;

    public Message() {
        id = UUID.randomUUID().toString();
        date = new Date();
    }

    @Id
    @Column(name = "id", length = 255)
    public String getId() {
        return id;
    }

    @java.beans.Transient
    public void setId(String id) {
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
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
