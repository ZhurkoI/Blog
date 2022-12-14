package org.zhurko.blog.controller;

import org.zhurko.blog.model.Label;
import org.zhurko.blog.repository.json.JsonLabelRepositoryImpl;
import org.zhurko.blog.repository.LabelRepository;

import java.util.List;

public class LabelController {

    private final LabelRepository repo = new JsonLabelRepositoryImpl();

    public Label saveLabel(String input) {
        return this.repo.save(new Label(input));
    }

    public Label findLabelByName(String name) {
        return this.repo.findByName(name);
    }

    public List<Label> getAll() {
        return this.repo.getAll();
    }

    public void deleteLabelById(Long id) {
        this.repo.deleteById(id);
    }

    public Label updateLabel(String existentName, String newName) {
        Label label = this.repo.findByName(existentName);
        label.setName(newName);
        return this.repo.update(label);
    }

    public Label getLabelById(Long id) {
        return this.repo.getById(id);
    }
}
