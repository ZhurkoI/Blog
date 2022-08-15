package org.zhurko.library.model;

import java.util.List;

public class Writer {

    private Long id;
    private String firstName;
    private String lastName;
    private List<Post> posts;

    public Writer(Long id, String firstName, String lastName, List<Post> posts) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.posts = posts;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<Post> getPosts() {
        return posts;
    }
}
