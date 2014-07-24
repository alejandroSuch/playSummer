package org.summerserver.model.vo;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Message {
    @Id
    @Column(name = "id", length = 32)
    protected String id;

    @Column(name = "create_at")
    protected Timestamp timestamp;

    @Embedded
    protected Author author;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
