package org.zhurko.library;

import org.zhurko.library.view.LabelView;
import org.zhurko.library.view.PostView;
import org.zhurko.library.view.WriterView;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    private static final String NEW_LINE = System.lineSeparator();

    public static void main(String[] args) {
        runMainMenu();
    }

    private static void runMainMenu() {
        while (true) {
            int choice = getChoice();
            switch (choice) {
                case 1:
                    WriterView writerView = new WriterView();
                    break;
                case 2:
                    PostView postView = new PostView();
                    break;
                case 3:
                    LabelView labelView = new LabelView();
                    labelView.runMenu();
                    break;
                case 0:
                    System.out.println("Exiting the program...");
                    System.exit(1);
            }
        }
    }

    private static int getChoice() {
        String mainMenu = "1 - Manage Writers" + NEW_LINE +
                "2 - Manage Posts" + NEW_LINE +
                "3 - Manage Labels" + NEW_LINE +
                "0 - Exit program";
        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        do {
            System.out.println();
            System.out.println("Main menu:");
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
