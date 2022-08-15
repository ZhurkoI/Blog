package org.zhurko.library.controller;

import org.zhurko.library.model.Label;
import org.zhurko.library.repository.JsonLabelRepositoryImpl;
import org.zhurko.library.repository.LabelRepository;

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
        Label updatedLabel = this.repo.update(label);

        if (updatedLabel != null) {
            return label;
        } else {
            return null;
        }
    }

    public Label findLabelById(Long id) {
        return this.repo.getById(id);
    }
}
