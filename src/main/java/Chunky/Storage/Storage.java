package main.java.Chunky.Storage;

import main.java.Chunky.Task.Deadlines;
import main.java.Chunky.Task.Events;
import main.java.Chunky.Task.Task;
import main.java.Chunky.Task.ToDo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Stores and loads the file containing the list of tasks
 */
public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Load file from filePath
     * @throws FileNotFoundException if filePath leads to a file that doesnt exist
     * @return main.java.Chunky.Chunky.main.java.Chunky.Task.Task List
     */
    public ArrayList<Task> load() throws IOException{
        ArrayList<Task> lst = new ArrayList<>();
        File file = new File(this.filePath);
        if (!file.exists()) {
            return lst;
        }
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Task task = parseTaskFromString(line);
                if (task != null) {
                    lst.add(task);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
        return lst;
    }

    /**
     * Saves the list of tasks into a file
     * @param tasks the list of tasks
     * @throws IOException when an invalid filepath is given
     */
    public void save(ArrayList<Task> tasks) throws IOException {
        try (FileWriter fw = new FileWriter(filePath, false)) {
            for (Task task : tasks) {
                fw.write(taskToString(task) + "\n");
            }
        }
    }

    /**
     * Convert lines in file to main.java.Chunky.Chunky.main.java.Chunky.Task.Task Objects
     * @param str line from the file
     * @return main.java.Chunky.Chunky.main.java.Chunky.Task.Task objects
     */
    private Task parseTaskFromString(String str) {
        String[] parts = str.split(" \\| ");
        if (parts.length < 3) return null;
        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        Task task;
        switch (type) {
            case "T":
                task = new ToDo(description);
                break;
            case "D":
                String by = parts[3]; // deadline info
                task = new Deadlines(description, by);
                break;
            case "E":
                String from = parts[3];
                String to = parts[4];
                task = new Events(description, from, to);
                break;
            default:
                return null;
        }
        if (isDone) {
            task.markAsDone();
        }
        return task;
    }

    /**
     * Converts the tasks into string format to be saved in the file
     * @param task the task to be converted
     * @return formatted string
     */
    private String taskToString(Task task) {
        String type;
        String extraInfo = "";

        if (task instanceof ToDo) {
            type = "T";
        } else if (task instanceof Deadlines) {
            type = "D";
            extraInfo = " | " + ((Deadlines) task).getBy();
        } else if (task instanceof Events) {
            type = "E";
            extraInfo = " | " + ((Events) task).getFrom() + " | " + ((Events) task).getTo();
        } else {
            return "";
        }

        String isDone = task.getDone() ? "1" : "0";
        return type + " | " + isDone + " | " + task.getDescription() + extraInfo;
    }
}
