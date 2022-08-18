package org.zhurko.library.view;

import org.zhurko.library.controller.LabelController;
import org.zhurko.library.controller.PostController;
import org.zhurko.library.model.Label;
import org.zhurko.library.model.Post;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class PostView {

    private static final String[] POST_MENU = {
            "0 - .. (Back to parent menu)",
            "1 - Create post",
            "2 - Get all posts",
            "3 - Get post by ID",
            "4 - Edit post",
            "5 - Add label to the post",
            "6 - Remove label from the post",
            "7 - Delete post by ID"
    };

    private final Scanner scanner = new Scanner(System.in);
    private final PostController postController = new PostController();
    private final LabelController labelController = new LabelController();

    public void runMenu() {
        while (true) {
            int choice = getChoice(POST_MENU);
            Long numberInput = -1L;

            switch (choice) {
                case 0:
                    return;
                case 1:
                    createPost();
                    break;
                case 2:
                    getAllPosts();
                    break;
                case 3:
                    getPostById();
                    break;
                case 4:
                    editPost();
                    break;
                case 5:
                    addLabelToPost();
                    break;
                case 6:
                    removeLabelFromPost();
                    break;
                case 7:
                    removePost(numberInput);
                    break;
            }
        }
    }

    private void removePost(Long numberInput) {
        System.out.print("Enter ID of the post you want to remove: ");
        try {
            numberInput = scanner.nextLong();
        } catch (InputMismatchException exception) {
            System.out.println("Invalid selection. Numbers only please.");
        }
        postController.deleteById(numberInput);
    }

    private void removeLabelFromPost() {
        Label label;
        Long numberInput;
        Post post;
        System.out.print("Enter ID of the post: ");
        numberInput = scanner.nextLong();
        scanner.nextLine();
        post = postController.findPostById(numberInput);
        if (post == null) {
            System.out.println("Post with ID=" + numberInput + " doesn't exist.");
            return;
        }

        if (post.getLabels().isEmpty()) {
            System.out.println("There are no labels assigned to the post");
            return;
        }
        System.out.println("List of labels assigned to the post:");
        post.getLabels().forEach(l -> System.out.println("ID=" + l.getId() + " | " + l.getName()));

        System.out.print("Enter ID of the label you want to remove: ");
        numberInput = scanner.nextLong();
        scanner.nextLine();
        label = labelController.findLabelById(numberInput);
        if (label == null) {
            System.out.println("There is no label with ID=" + numberInput + " assigned to selected post.");
            return;
        }

        post = postController.removeLabel(post, label);
        System.out.println("Label removed:");
        printPosts(post);
    }

    private void addLabelToPost() {
        Long numberInput;
        Post post;
        System.out.print("Enter ID of the post: ");
        numberInput = scanner.nextLong();
        scanner.nextLine();
        post = postController.findPostById(numberInput);
        if (post == null) {
            System.out.println("Post with ID=" + numberInput + " doesn't exist.");
            return;
        }

        // TODO: Это нормально, когда в PostVew создается LabelController для взаимодействия с лейблами
        System.out.println("List of available labels:");
        labelController.getAll().forEach(System.out::println);
        System.out.print("Enter ID of the label: ");
        numberInput = scanner.nextLong();
        scanner.nextLine();
        Label label = labelController.findLabelById(numberInput);
        if (label == null) {
            System.out.println("Label with ID=" + numberInput + " doesn't exist.");
            return;
        }

        post = postController.addLabel(post, label);
        System.out.println("Label added:");
        printPosts(post);
    }

    private void editPost() {
        Long numberInput;
        Post post;
        String stringInput;
        System.out.print("Enter ID of the post you want to edit: ");
        numberInput = scanner.nextLong();
        scanner.nextLine();  // to read the '\n' character which is present in console after executing scanner.nextLong()
        post = postController.findPostById(numberInput);
        if (post == null) {
            System.out.println("Post with ID=" + numberInput + " doesn't exist.");
            return;
        }

        System.out.print("Enter new content: ");
        stringInput = scanner.nextLine();
        post = postController.updatePost(post.getId(), stringInput);
        System.out.println("Post with ID=" + post.getId() + " has been updated. New post content: ");
        printPosts(post);
    }

    private void getPostById() {
        Post post;
        Long numberInput;
        System.out.print("Enter ID of the post: ");
        numberInput = scanner.nextLong();
        scanner.nextLine();
        post = postController.findPostById(numberInput);
        if (post != null) {
            System.out.println("Post found:");
            printPosts(post);
        } else {
            System.out.println("Post with ID=" + numberInput + " doesn't exist.");
        }
    }

    private void getAllPosts() {
        List<Post> allPosts = postController.getAll();
        if (!allPosts.isEmpty()) {
            System.out.println("List of available posts:");
            printPosts(allPosts);
        } else {
            System.out.println("No posts exist.");
        }
    }

    private void createPost() {
        String stringInput;
        System.out.print("Enter post content: ");
        stringInput = scanner.nextLine();
        Post createdPost = postController.saveNewPost(stringInput);

        if (createdPost != null) {
            System.out.println("Post has been created:");
            printPosts(createdPost);
        } else {
            System.out.println("Post has not been created.");
        }
    }

    private int getChoice(String[] menuEntries) {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        do {
            System.out.println();
            System.out.println("Post Menu:");
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

    private String printListOfLabels(Post post) {
        if (post.getLabels().isEmpty()) {
            return "<no labels>";
        } else {
            return post.getLabels().stream()
                    .map(Label::getName)
                    .collect(Collectors.joining(", "));
        }
    }

    private void printPosts(List<Post> posts) {
        posts.forEach(p -> System.out.println("ID=" + p.getId() +
                " | CREATED: " + p.getCreated() +
                " | UPDATED: " + p.getUpdated() +
                " | STATUS: " + p.getPostStatus() +
                " | LABELS: " + printListOfLabels(p) +
                " | CONTENT: " + p.getContent()
        ));
    }

    private void printPosts(Post post) {
        System.out.println("ID=" + post.getId() +
                " | CREATED: " + post.getCreated() +
                " | UPDATED: " + post.getUpdated() +
                " | STATUS: " + post.getPostStatus() +
                " | LABELS: " + printListOfLabels(post) +
                " | CONTENT: " + post.getContent()
        );
    }
}
