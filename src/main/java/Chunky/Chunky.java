package Chunky;

import Chunky.Command.Command;
import Chunky.Command.ByeCommand;
import Chunky.Command.ListCommand;
import Chunky.Command.AddTaskCommand;
import Chunky.Command.DeleteCommand;
import Chunky.Command.MarkCommand;
import Chunky.Command.UnmarkCommand;
import Chunky.Command.FindCommand;
import Chunky.Parser.Parser;
import Chunky.Storage.Storage;
import Chunky.Task.Task;
import Chunky.Task.TaskList;
import Chunky.Ui.Ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;


public class Chunky {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private Map<String, Command> commands;

    public Chunky(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        try {
            this.tasks = new TaskList(this.storage.load());
        } catch (IOException e) {
            this.tasks = new TaskList();
        }

        // Initialize command map
        this.commands = new HashMap<>();
        commands.put("list", new ListCommand());
        commands.put("mark", new MarkCommand());
        commands.put("unmark", new UnmarkCommand());
        commands.put("delete", new DeleteCommand());
        commands.put("todo", new AddTaskCommand());
        commands.put("deadline", new AddTaskCommand());
        commands.put("event", new AddTaskCommand());
        commands.put("find", new FindCommand());
    }

    /**
     * Generates a response for the user's chat message using Command pattern.
     * @param input gets input to see which command is entered
     * @return executes the command and returns Chunk's reply
     */
    public String getResponse(String input) {
        if (input.trim().isEmpty()) {
            return "Please enter a command!";
        }

        try {
            String commandName = Parser.getCommand(input);

            if (commandName.equals("bye")) {
                storage.save(tasks.getTasks());
                return "Bye! Hope to see you again soon!\nTasks have been saved.";
            }

            Command command = commands.get(commandName);
            if (command != null) {
                return command.execute(input, tasks, storage);
            } else {
                return "Unknown command: " + commandName;
            }

        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * Runs Chunky
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String input = ui.readCommand();
                ui.showLine();
                String response = getResponse(input);
                System.out.println(response);

                if (Parser.getCommand(input).equals("bye")) {
                    isExit = true;
                }
            } catch (Exception e) {
                ui.showError(e.getMessage());
            }
            ui.showLine();
        }
        ui.close();
    }

    public static void main(String[] args) {
        new Chunky("ip/Chunky.txt").run();
    }
}