package org.zhurko.blog.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.zhurko.blog.model.Writer;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class JsonWriterRepositoryImpl implements WriterRepository {

    private static final String FILE_PATH = "src/main/resources/writers.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public Writer getById(Long id) {
        return getAllWritersInternal().stream()
                .filter(w -> w.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Writer> getAll() {
        return getAllWritersInternal();
    }

    @Override
    public Writer save(Writer writer) {
        List<Writer> writers = getAllWritersInternal();
        writer.setId(generateId(writers));
        List<Writer> modifiableListOfWriters = new ArrayList<>(writers);
        modifiableListOfWriters.add(writer);
        writeToFile(modifiableListOfWriters);
        return writer;
    }

    @Override
    public Writer update(Writer writer) {
        List<Writer> writers = getAllWritersInternal();
        writers.forEach(w -> {
                    if (w.getId().equals(writer.getId())) {
                        w.setFirstName(writer.getFirstName());
                        w.setLastName(writer.getLastName());
                        w.setPosts((writer.getPosts()));
                    }
                }
        );
        writeToFile(writers);

        return writer;
    }

    @Override
    public void deleteById(Long id) {
        List<Writer> writers = getAllWritersInternal();
        writers.removeIf(w -> w.getId().equals(id));
        writeToFile(writers);
    }

    private List<Writer> getAllWritersInternal() {
        List<Writer> writers;

        Type listType = new TypeToken<ArrayList<Writer>>() {
        }.getType();

        try (FileReader reader = new FileReader(FILE_PATH)) {
            writers = this.gson.fromJson(reader, listType);
        } catch (IOException exception) {
            exception.printStackTrace();
            return Collections.emptyList();
        }

        if (writers == null) {
            return Collections.emptyList();
        } else {
            return writers;
        }
    }

    private Long generateId(List<Writer> writers) {
        long maxIndex;
        Optional<Writer> mostRecentEntry = writers.stream().max(Comparator.comparingLong(Writer::getId));
        if (mostRecentEntry.isPresent()) {
            maxIndex = mostRecentEntry.get().getId() + 1L;
        } else {
            maxIndex = 1L;
        }
        return maxIndex;
    }

    private void writeToFile(List<Writer> writers) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            this.gson.toJson(writers, writer);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
