package org.zhurko.library.repository;

import org.zhurko.library.model.Label;
import org.zhurko.library.model.Post;

public interface PostRepository extends GenericRepository<Post, Long> {

    Post addLabel(Post post, Label label);

    Post removeLabel(Post post, Label label);
}
