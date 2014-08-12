package org.summerserver.model.vo;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class Author {
    protected String name;

    public Author() {
    }

    public Author(String name) {
        this.name = name;
    }

    @Column(name = "author_name")
    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
