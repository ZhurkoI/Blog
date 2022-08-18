package org.zhurko.library.view;

import org.zhurko.library.controller.LabelController;
import org.zhurko.library.model.Label;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class LabelView {

    private static final String[] LABEL_MENU = {
            "0 - .. (Back to parent menu)",
            "1 - Create label",
            "2 - Get all labels",
            "3 - Get label by name",
            "4 - Get label by ID",
            "5 - Edit label",
            "6 - Delete label by ID"
    };
    private final Scanner scanner = new Scanner(System.in);
    private final LabelController labelController = new LabelController();

    public void runMenu() {
        while (true) {
            int choice = getChoice(LABEL_MENU);
            String stringInput;
            Long numberInput = -1L;
            Label label;

            switch (choice) {
                case 0:
                    return;
                case 1:
                    System.out.print("Enter name of the label: ");
                    stringInput = scanner.nextLine();
                    Label createdLabel = labelController.saveLabel(stringInput);

                    if (createdLabel != null) {
                        System.out.println("Label has been created: " + createdLabel);
                    } else {
                        System.out.println("Label '" + stringInput + "' has not been created.");
                    }
                    break;
                case 2:
                    List<Label> allLabels = labelController.getAll();
                    if (!allLabels.isEmpty()) {
                        System.out.println("List of available labels:");
                        allLabels.forEach(n -> System.out.println("   * " + n));
                    } else {
                        System.out.println("No labels exist.");
                    }
                    break;
                case 3:
                    System.out.print("Enter name of the label: ");
                    stringInput = scanner.nextLine();
                    label = labelController.findLabelByName(stringInput);
                    if (label != null) {
                        System.out.println("Label exist: " + label);
                    } else {
                        System.out.println("Label '" + stringInput + "' doesn't exist.");
                    }
                    break;
                case 4:
                    System.out.print("Enter label ID: ");
                    numberInput = scanner.nextLong();
                    label = labelController.findLabelById(numberInput);
                    if (label != null) {
                        System.out.println("Label found: " + label);
                    } else {
                        System.out.println("Label with ID=" + numberInput + " doesn't exist.");
                    }
                    break;
                case 5:
                    System.out.print("Enter name of the label you want to edit: ");
                    stringInput = scanner.nextLine();
                    label = labelController.findLabelByName(stringInput);
                    if (label == null) {
                        System.out.println("Label '" + stringInput + "' doesn't exist.");
                        break;
                    }
                    System.out.print("Enter new name of the label: ");
                    String stringInput2 = scanner.nextLine();
                    label = labelController.updateLabel(stringInput, stringInput2);
                    System.out.println("Label has been renamed. New name: " + label);
                    break;
                case 6:
                    System.out.print("Enter ID of the label you want to remove: ");
                    try {
                        numberInput = scanner.nextLong();
                    } catch (InputMismatchException exception) {
                        System.out.println("Invalid selection. Numbers only please.");
                    }
                    labelController.deleteLabelById(numberInput);
                    break;
            }
        }
    }

    private int getChoice(String[] menuEntries) {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        do {
            System.out.println();
            System.out.println("Label menu:");
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
}
