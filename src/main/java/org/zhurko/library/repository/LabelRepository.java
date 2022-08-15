package org.zhurko.library.repository;

import org.zhurko.library.model.Label;

public interface LabelRepository extends GenericRepository<Label, Long> {
    Label findByName(String name);
}
