package org.summerserver.model.vo;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Author {
    @Column(name = "author_name")
    protected String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
