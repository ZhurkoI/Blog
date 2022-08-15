package org.zhurko.library.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.zhurko.library.model.Label;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class JsonLabelRepositoryImpl implements LabelRepository {

    private static final String FILE_PATH = "JSON files/labels.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public Label getById(Long id) {
        return null;
    }

    @Override
    public List<Label> getAll() {
        return getAllLabelsInternal();
    }

    @Override
    public Label save(Label label) {
        if (findByName(label.getName()) != null) {
            return label;
        }

        List<Label> labels = getAllLabelsInternal();
        long maxIndex;
        Optional<Label> mostRecentLabel = labels.stream().max(Comparator.comparingLong(Label::getId));
        if (mostRecentLabel.isPresent()) {
            maxIndex = mostRecentLabel.get().getId();
        } else {
            maxIndex = 0;
        }
        label.setId(maxIndex + 1);
        labels.add(label);

        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            this.gson.toJson(labels, writer);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        labels = getAllLabelsInternal();

        return labels
                .stream()
                .filter(n -> label.getName().equals(n.getName()))
                .findAny()
                .orElse(null);
    }

    // TODO: The method doesn't handle the case is new name duplicates an existing one
    @Override
    public Label update(Label label) {
        List<Label> labels = getAllLabelsInternal();
        labels.replaceAll(n -> {
            if (n.getId() == label.getId()) {
                n.setName(label.getName());
            }
            return n;
        });

        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            this.gson.toJson(labels, writer);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return getAllLabelsInternal()
                .stream()
                .filter(n -> label.getName().equals(n.getName()))
                .findAny()
                .orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        List<Label> labels = getAllLabelsInternal();
        labels.removeIf(n -> n.getId() == id);

        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            this.gson.toJson(labels, writer);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private List<Label> getAllLabelsInternal() {
        List<Label> labels = new ArrayList<>();
        Type listType = new TypeToken<ArrayList<Label>>() {
        }.getType();

        try (FileReader reader = new FileReader(FILE_PATH)) {
            labels = this.gson.fromJson(reader, listType);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        if (labels == null) {
            return new ArrayList<>();
        } else {
            return labels;
        }
    }

    @Override
    public Label findByName(String name) {
        List<Label> labelEntries = getAllLabelsInternal();
        return labelEntries
                .stream()
                .filter(n -> n.getName().equals(name))
                .findAny()
                .orElse(null);
    }
}
