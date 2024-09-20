package charlotte.ui;

import java.util.Scanner;

/**
 * Handles the user interface interactions for the Charlotte chatbot.
 * The Ui class is responsible for reading user input and displaying messages and prompts to the user.
 * It provides methods to print messages, handle command input, and display error or other messages.
 */
public class Ui {
    private final Scanner scanner;

    /**
     * Constructs a Ui instance with a new Scanner for reading user input.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
        assert scanner != null : "Scanner should be initialised properly";
    }

    /**
     * Reads the next line of user input from the console.
     *
     * @return The line of input entered by the user.
     */
    public String readCommand() {
        String input = scanner.nextLine();
        assert input != null : "User input should not be null";
        return input;
    }

    /**
     * Prints a horizontal line to the console for formatting purposes.
     * This line is used to separate different sections of the UI output.
     */
    public String printLine() {
        return "______________________________________";
    }

    /**
     * Prints a welcome message to the console when the application starts.
     * This method displays a greeting and prompts the user to enter commands.
     */
    public String printWelcome() {
        return "Hello! I'm Charlotte!\nWhat can I do for you?";
    }

    /**
     * Prints an exit message to the console when the application is about to close.
     * This method displays a goodbye message to the user.
     */
    public String printExit() {
        return "Bye. Hope to see you again soon!";
    }

    /**
     * Displays an error message indicating that no data file was found.
     * This method is called when the application fails to locate the existing data file.
     */
    public String showLoadingError() {
        return "No existing data file found";
    }

    /**
     * Displays an error message to the user.
     *
     * @param message The error message to be displayed.
     */
    public String showError(String message) {
        assert message != null : "Error message should not be null";
        return message;
    }

    /**
     * Displays a message to the user.
     *
     * @param message The message to be displayed.
     */
    public String showMessage(String message) {
        assert message != null : "Display message should not be null";
        return message;
    }
}
