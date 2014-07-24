package org.summerserver.model.vo;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@org.hibernate.annotations.Entity(dynamicUpdate = true)
@Table(name = "comments")
public class Comment extends Message {
    StatusUpdate statusUpdate;

    @ManyToOne
    @JoinColumn(name = "id_status_update")
    public StatusUpdate getStatusUpdate() {
        return statusUpdate;
    }

    public void setStatusUpdate(StatusUpdate statusUpdate) {
        this.statusUpdate = statusUpdate;
    }
}
