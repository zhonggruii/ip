import java.io.IOException;

public class Chunky {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public Chunky(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        try {
            this.tasks = new TaskList(this.storage.load());
        } catch (IOException e) {
            this.ui.showError("Could not load existing tasks: " + e.getMessage());
            this.tasks = new TaskList();
        }
    }

    public void run() {
        ui.showWelcome();

        boolean isExit = false;
        while (!isExit) {
            try {
                String input = ui.readCommand();
                ui.showLine();

                String command = Parser.getCommand(input);

                if (command.equals("bye")) {
                    storage.save(tasks.getTasks());
                    isExit = true;
                } else if (command.equals("list")) {
                    ui.showTaskList(tasks);
                } else if (command.equals("mark")) {
                    int index = Parser.parseTaskIndex(Parser.getArguments(input));
                    if (index >= tasks.size()) {
                        throw new InvalidMessageException("Invalid task number!");
                    }
                    tasks.get(index).markAsDone();
                    ui.showTaskMarked(tasks.get(index));
                } else if (command.equals("unmark")) {
                    int index = Parser.parseTaskIndex(Parser.getArguments(input));
                    if (index >= tasks.size()) {
                        throw new InvalidMessageException("Invalid task number!");
                    }
                    tasks.get(index).unmarkDone();
                    ui.showTaskUnmarked(tasks.get(index));
                } else if (command.equals("delete")) {
                    int index = Parser.parseTaskIndex(Parser.getArguments(input));
                    if (index >= tasks.size()) {
                        throw new InvalidMessageException("Invalid task number!");
                    }
                    Task deletedTask = tasks.get(index);
                    tasks.delete(index);
                    ui.showTaskDeleted(deletedTask, tasks.size());
                } else if (command.equals("todo") || command.equals("deadline") || command.equals("event")) {
                    Task newTask = Parser.parseTask(input);
                    tasks.add(newTask);
                    ui.showTaskAdded(newTask, tasks.size());
                } else {
                    throw new InvalidMessageException("Unknown command: " + command);
                }

            } catch (ChunkyException | IOException e) {
                ui.showError(e.getMessage());
            }
            ui.showLine();
        }

        ui.showGoodbye();
        ui.close();
    }

    public static void main(String[] args) {
        new Chunky("../Chunky.txt").run();
    }
}