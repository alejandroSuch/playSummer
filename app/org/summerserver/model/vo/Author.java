package org.summerserver.model.vo;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Author {
    protected String name;

    public Author() {
    }

    public Author(String name) {
        this.name = name;
    }

    @Column(name = "author_name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
