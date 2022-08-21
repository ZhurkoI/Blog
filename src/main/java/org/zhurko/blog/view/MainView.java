package org.zhurko.blog.view;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MainView {

    private static final String NEW_LINE = System.lineSeparator();

    private final WriterView writerView = new WriterView();
    private final PostView postView = new PostView();
    private final LabelView labelView = new LabelView();
    private final Scanner scanner = new Scanner(System.in);

    public void runMainMenu() {
        while (true) {
            int choice = getChoice();
            switch (choice) {
                case 1:
                    writerView.runMenu();
                    break;
                case 2:
                    postView.runMenu();
                    break;
                case 3:
                    labelView.runMenu();
                    break;
                case 0:
                    System.out.println("Exiting the program...");
                    System.exit(1);
            }
        }
    }

    private int getChoice() {
        String mainMenu = "1 - Manage Writers" + NEW_LINE +
                "2 - Manage Posts" + NEW_LINE +
                "3 - Manage Labels" + NEW_LINE +
                "0 - Exit program";
        int choice = -1;

        do {
            System.out.println();
            System.out.println("Main Menu:");
            System.out.println(mainMenu);
            System.out.print("Please make a selection: ");
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid selection. Numbers only please.");
                scanner.next();
            }
        } while (choice < 0 || choice > 3);
        return choice;
    }
}
