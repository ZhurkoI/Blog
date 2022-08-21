package org.zhurko.blog.util;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInputReader {

    private UserInputReader() {
    }

    public static Long readNumberInput() {
        Long numberInput = -1L;
        try {
            Scanner scanner = new Scanner(System.in);
            numberInput = scanner.nextLong();
            scanner.nextLine();
        } catch (InputMismatchException exception) {
            System.out.println("Invalid selection. Numbers only please.");
        }
        return numberInput;
    }
}
