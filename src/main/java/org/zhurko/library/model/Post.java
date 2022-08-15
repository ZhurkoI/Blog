package org.zhurko.library.model;

import java.util.Date;
import java.util.List;

public class Post {

    private Long id;
    private String content;
    private Date created;
    private Date updated;
    private List<Label> labels;
    private PostStatus postStatus;

    public Post(Long id, String content, Date created, Date updated, List<Label> labels, PostStatus postStatus) {
        this.id = id;
        this.content = content;
        this.created = created;
        this.updated = updated;
        this.labels = labels;
        this.postStatus = postStatus;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Date getCreated() {
        return created;
    }

    public Date getUpdated() {
        return updated;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public PostStatus getPostStatus() {
        return postStatus;
    }
}
