package org.zhurko.blog.view;

import org.zhurko.blog.controller.PostController;
import org.zhurko.blog.controller.WriterController;
import org.zhurko.blog.model.Post;
import org.zhurko.blog.model.Writer;
import org.zhurko.blog.util.UserInputReader;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

public class WriterView {

    private static final String[] WRITER_MENU = {
            "0 - .. (Back to parent menu)",
            "1 - Create writer",
            "2 - Get all writers",
            "3 - Get writer by ID",
            "4 - Edit writer",
            "5 - Add post to the writer",
            "6 - Remove post from the writer",
            "7 - Delete writer by ID"
    };

    private final Scanner scanner = new Scanner(System.in);
    private final WriterController writerController = new WriterController();
    private final PostController postController = new PostController();

    public void runMenu() {
        while (true) {
            int choice = getChoice(WRITER_MENU);

            switch (choice) {
                case 0:
                    return;
                case 1:
                    createWriter();
                    break;
                case 2:
                    getAllWriters();
                    break;
                case 3:
                    getWriterById();
                    break;
                case 4:
                    editWriter();
                    break;
                case 5:
                    addPostToWriter();
                    break;
                case 6:
                    removePostFromWriter();
                    break;
                case 7:
                    removeWriter();
                    break;
            }
        }
    }

    private void createWriter() {
        System.out.print("Enter first name: ");
        String stringInput1 = scanner.nextLine();
        System.out.print("Enter last name: ");
        String stringInput2 = scanner.nextLine();
        Writer createdWriter = writerController.saveNewWriter(stringInput1, stringInput2);
        if (createdWriter != null) {
            System.out.println("Writer has been created:");
            printWriters(createdWriter);
        } else {
            System.out.println("Writer has not been created.");
        }
    }

    private void getAllWriters() {
        List<Writer> allWriters = writerController.getAll();
        if (!allWriters.isEmpty()) {
            System.out.println("List of writers:");
            printWriters(allWriters);
        } else {
            System.out.println("No writers exist.");
        }
    }

    private void getWriterById() {
        System.out.print("Enter ID of the writer: ");
        Long numberInput = UserInputReader.readNumberInput();
        Writer writer = writerController.getWriterById(numberInput);
        if (writer != null) {
            System.out.println("Writer is:");
            printWriters(writer);
        } else {
            System.out.println("Writer with ID=" + numberInput + " doesn't exist.");
        }
    }

    private void editWriter() {
        System.out.print("Enter ID of the writer you want to edit: ");
        Long numberInput = UserInputReader.readNumberInput();
        Writer writer = writerController.getWriterById(numberInput);
        if (writer == null) {
            System.out.println("Writer with ID=" + numberInput + " doesn't exist.");
            return;
        }

        System.out.print("Enter new writer's first name: ");
        String stringInput1 = scanner.nextLine();
        System.out.print("Enter new writer's last name: ");
        String stringInput2 = scanner.nextLine();
        writer = writerController.updateWriter(writer.getId(), stringInput1, stringInput2);
        System.out.println("Writer with ID=" + writer.getId() + " has been updated: ");
        printWriters(writer);
    }

    private void addPostToWriter() {
        System.out.print("Enter ID of the writer: ");
        Long numberInput = UserInputReader.readNumberInput();
        Writer writer = writerController.getWriterById(numberInput);
        if (writer == null) {
            System.out.println("Writer with ID=" + numberInput + " doesn't exist.");
            return;
        }

        System.out.println("List of available posts:");
        postController.getAll().forEach(p -> System.out.println("ID=" + p.getId() + " | CONTENT: " + p.getContent()));

        System.out.print("Enter ID of the post: ");
        numberInput = UserInputReader.readNumberInput();
        Post post = postController.getPostById(numberInput);
        if (post == null) {
            System.out.println("Post with ID=" + numberInput + " doesn't exist.");
            return;
        }

        writer = writerController.addPost(writer.getId(), post.getId());
        System.out.println("Post has been added:");
        printWriters(writer);
    }

    private void removePostFromWriter() {
        System.out.print("Enter ID of the writer: ");
        Long numberInput = UserInputReader.readNumberInput();
        Writer writer = writerController.getWriterById(numberInput);
        if (writer == null) {
            System.out.println("Writer with ID=" + numberInput + " doesn't exist.");
            return;
        }

        if (writer.getPosts().isEmpty()) {
            System.out.println("Selected writer has no posts.");
            return;
        }

        System.out.println("There are posts by selected writer:");
        writer.getPosts().forEach(p -> System.out.println("ID=" + p.getId() + " | CONTENT: " + p.getContent()));

        System.out.print("Enter ID of the post you want to remove: ");
        numberInput = UserInputReader.readNumberInput();
        Post post = postController.getPostById(numberInput);
        if (post == null) {
            System.out.println("There is no post with ID=" + numberInput + ".");
            return;
        }

        writer = writerController.removePost(writer.getId(), post.getId());
        System.out.println("Post has been removed:");
        printWriters(writer);
    }

    private void removeWriter() {
        Long numberInput = UserInputReader.readNumberInput();
        writerController.deleteById(numberInput);
    }

    private int getChoice(String[] menuEntries) {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        do {
            System.out.println();
            System.out.println("Writer Menu:");
            Arrays.stream(menuEntries).forEach(System.out::println);
            System.out.print("Please make a selection: ");
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid selection. Numbers only please.");
                scanner.next();
            }
        } while (choice < 0 || choice > menuEntries.length);
        return choice;
    }

    private String getRelatedPosts(Writer writer) {
        if (writer.getPosts().isEmpty()) {
            return "<no posts>";
        } else {
            return writer.getPosts().stream()
                    .map(Post::getId)
                    .map(Objects::toString)
                    .collect(Collectors.joining(", "));
        }
    }

    private void printWriters(List<Writer> writers) {
        writers.forEach(w -> System.out.println("ID=" + w.getId() +
                " | FIRST NAME: " + w.getFirstName() +
                " | LAST NAME: " + w.getLastName() +
                " | POST ID: " + getRelatedPosts(w)));
    }

    private void printWriters(Writer writer) {
        System.out.println("ID=" + writer.getId() +
                " | FIRST NAME: " + writer.getFirstName() +
                " | LAST NAME: " + writer.getLastName() +
                " | POST ID: " + getRelatedPosts(writer)
        );
    }
}
