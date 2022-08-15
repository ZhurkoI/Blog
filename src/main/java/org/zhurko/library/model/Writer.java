package org.zhurko.library.model;

import java.util.List;

public class Writer {

    private long id;
    private String firstName;
    private String lastName;
    private List<Post> posts;

    public Writer(long id, String firstName, String lastName, List<Post> posts) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.posts = posts;
    }

    public long getId() {
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
