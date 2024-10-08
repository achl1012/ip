package charlotte.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import charlotte.exception.CharlotteException;
import charlotte.task.Deadline;
import charlotte.task.Event;
import charlotte.task.Task;
import charlotte.task.TaskList;
import charlotte.task.ToDo;

/**
 * The Storage class handles the loading and saving of tasks from/to a file.
 */
public class Storage {
    private static final ArrayList<Task> TASKS = new ArrayList<>();
    private final String filePath;
    /**
     * Constructs a Storage object with the specified file path.
     *
     * @param filePath The path of the file where tasks are stored.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Saves the current list of tasks to the file specified in filePath.
     *
     * @param tasks The TaskList containing tasks to be saved.
     * @throws CharlotteException If an error occurs while saving the tasks.
     */
    public void saveTasks(TaskList tasks) throws CharlotteException {
        assert tasks != null : "TaskList should not be null";
        try {
            File file = new File(filePath);
            File directory = file.getParentFile();

            if (!directory.exists()) {
                boolean isCreated = directory.mkdirs();
                if (!isCreated) {
                    throw new CharlotteException("Failed to create directory: " + directory.getAbsolutePath());
                }
            }

            if (!file.exists()) {
                file.createNewFile();
                assert file.exists() : "Failed to create file: " + filePath;
            }

            FileWriter fw = new FileWriter(filePath);
            for (Task task : tasks.getTasks()) {
                fw.write(task.toFileFormat() + System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            throw new CharlotteException("An error occurred while saving the file.");
        }
    }

    /**
     * Loads tasks from the file specified in filePath.
     *
     * @return An ArrayList of tasks loaded from the file.
     * @throws CharlotteException If the file does not exist or contains invalid data.
     */
    public ArrayList<Task> loadTasks() throws CharlotteException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        assert filePath != null && !filePath.isEmpty() : "File path should not be null or empty";

        if (!file.exists()) {
            throw new CharlotteException("No existing data file found");
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String nextTask = scanner.nextLine();
                String[] taskData = nextTask.split(" \\| ");
                assert taskData.length >= 3 : "Invalid task format in file";
                tasks.add(parseTask(taskData));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
        } catch (CharlotteException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
        return tasks;
    }

    /**
     * Parses a task from the given task data array.
     *
     * @param taskData An array containing the task type, status, description, and other task-specific details.
     * @return The parsed task.
     * @throws CharlotteException If the task data is invalid or the task type is unknown.
     */
    private Task parseTask(String[] taskData) throws CharlotteException {
        String taskType = taskData[0];
        boolean isDone = taskData[1].equals("1");
        String description = taskData[2];

        Task task;
        switch (taskType) {
        case "T":
            task = new ToDo(description);
            break;
        case "D":
            String by = getTaskDetail(taskData, 3, "Invalid format for a deadline task");
            task = new Deadline(description, by);
            break;
        case "E":
            String[] eventParts = getTaskDetail(taskData, 3, "Invalid format for an event task")
                    .split(" to ");
            task = new Event(description, eventParts[0], eventParts[1]);
            break;
        default:
            throw new CharlotteException("Unknown task type in file: " + taskType);
        }

        if (isDone) {
            task.markAsDone();
        }
        return task;
    }

    private String getTaskDetail(String[] taskData, int index, String errorMessage) throws CharlotteException {
        if (taskData.length <= index) {
            throw new CharlotteException(errorMessage);
        }
        return taskData[index];
    }
}
