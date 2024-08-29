public class AddCommand extends Command {
    private final Task task;

    public AddCommand(Task task) {
        this.task = task;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws CharlotteException {
        tasks.addTask(task);
        ui.showMessage("Got it. I've added this task:\n  " + task
                + "\n Now you have " + tasks.getSize() + " tasks in the list.");
        storage.saveTasks(tasks);
    }
}
