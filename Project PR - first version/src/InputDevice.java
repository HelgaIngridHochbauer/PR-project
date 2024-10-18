import java.util.Scanner;

public class InputDevice {
    private Scanner scanner;

    public InputDevice() {
        this.scanner = new Scanner(System.in);
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