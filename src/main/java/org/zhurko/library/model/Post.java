package org.zhurko.library.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Post {

    private Long id;
    private String content;
    private Date created;
    private Date updated;
    private Set<Label> labels = new HashSet<>();
    private PostStatus postStatus;

    public Post(Long id, String content, Date created, Date updated, Set<Label> labels, PostStatus postStatus) {
        this.id = id;
        this.content = content;
        this.created = created;
        this.updated = updated;
        this.labels = labels;
        this.postStatus = postStatus;
    }

    public Post(String content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Set<Label> getLabels() {
        return labels;
    }

    public void addLabel(Label label) {
        this.labels.add(label);
    }

    public void setAllLabels(Set<Label> labels) {
        this.labels = labels;
    }

    public PostStatus getPostStatus() {
        return postStatus;
    }

    public void setPostStatus(PostStatus postStatus) {
        this.postStatus = postStatus;
    }
}
