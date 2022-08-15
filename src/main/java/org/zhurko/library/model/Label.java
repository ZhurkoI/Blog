package org.zhurko.library.model;

public class Label {

    private Long id;
    private String name;

    public Label(String name) {
        this.name = name;
    }

    public Label(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name + " (id=" + id + ')';
    }
}
