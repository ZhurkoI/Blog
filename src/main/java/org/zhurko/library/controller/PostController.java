package org.zhurko.library.controller;

import org.zhurko.library.model.Label;
import org.zhurko.library.model.Post;
import org.zhurko.library.repository.JsonPostRepositoryImpl;
import org.zhurko.library.repository.PostRepository;

import java.util.List;

public class PostController {

    private final PostRepository repo = new JsonPostRepositoryImpl();

    public Post saveNewPost(String input) {
        return this.repo.save(new Post(input));
    }

    public List<Post> getAll() {
        return this.repo.getAll();
    }

    public Post findPostById(Long id) {
        return this.repo.getById(id);
    }

    public void deleteById(Long id) {
        this.repo.deleteById(id);
    }

    public Post updatePost(Long id, String newContent) {
        Post post = this.repo.getById(id);
        post.setContent(newContent);
        return this.repo.update(post);
    }

    public Post addLabel(Post post, Label label) {
        return this.repo.addLabel(post, label);
    }

    public Post removeLabel(Post post, Label label) {
        return this.repo.removeLabel(post, label);
    }
}
