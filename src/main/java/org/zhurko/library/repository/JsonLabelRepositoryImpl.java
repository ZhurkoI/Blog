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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class JsonLabelRepositoryImpl implements LabelRepository {

    private static final String FILE_PATH = "src/main/resources/labels.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public Label getById(Long id) {
        return getAllLabelsInternal().stream()
                .filter(l -> l.getId().equals(id))
                .findFirst()
                .orElse(null);
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
        label.setId(generateId(labels));
        List<Label> modifiableListOfLabels = new ArrayList<>(labels);
        modifiableListOfLabels.add(label);
        writeToFile(modifiableListOfLabels);
        return label;
    }

    @Override
    public Label update(Label label) {
        List<Label> labels = getAllLabelsInternal();
        labels.forEach(n -> {
            if (n.getId().equals(label.getId())) {
                n.setName(label.getName());
            }
        });
        writeToFile(labels);
        return label;
    }

    @Override
    public void deleteById(Long id) {
        List<Label> labels = getAllLabelsInternal();
        labels.removeIf(n -> n.getId().equals(id));
        writeToFile(labels);
    }

    private List<Label> getAllLabelsInternal() {
        List<Label> labels;
        Type listType = new TypeToken<ArrayList<Label>>() {
        }.getType();

        try (FileReader reader = new FileReader(FILE_PATH)) {
            labels = this.gson.fromJson(reader, listType);
        } catch (IOException exception) {
            exception.printStackTrace();
            return Collections.emptyList();
        }

        if (labels == null) {
            return Collections.emptyList();
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

    private void writeToFile(List<Label> labels) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            this.gson.toJson(labels, writer);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private Long generateId(List<Label> labels) {
        long maxIndex;
        Optional<Label> mostRecentEntry = labels.stream().max(Comparator.comparingLong(Label::getId));
        if (mostRecentEntry.isPresent()) {
            maxIndex = mostRecentEntry.get().getId() + 1L;
        } else {
            maxIndex = 1L;
        }
        return maxIndex;
    }
}
