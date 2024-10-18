import model.*;

public class Main {
    public static void main(String[] args) {
        InputDevice inputDevice = new InputDevice();
        OutputDevice outputDevice = new OutputDevice();
        Application app = new Application(inputDevice, outputDevice);

        if (args.length > 0) {
            // If command-line arguments are provided, use them directly
            app.processArguments(args);
        } else {
            // If no command-line arguments, prompt for input
            app.run();
        }

        inputDevice.close();
    }
}