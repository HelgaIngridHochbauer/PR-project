package com.Midterm.utils;

import java.util.Scanner;

public class InputDevice {
    private Scanner scanner;

    public InputDevice() {
        this.scanner = new Scanner(System.in);
    }

    public String readLine() {
        return scanner.nextLine();
    }

    public int readInt() {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter an integer:");
            scanner.next();  // Discard invalid input
        }
        return scanner.nextInt();
    }

    public String[] getArguments() {
        System.out.println("Enter command and arguments (space-separated):");
        String input = scanner.nextLine();
        return input.split("\\s+");
    }

    public void close() {
        scanner.close();
    }
}