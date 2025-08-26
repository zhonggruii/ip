import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Chunky{
    public static List<Task> tasks = new ArrayList<>();
    public static String textToAdd = "";

    /**
     * Writes the chatbot's tasks into a file and stores it
     * as Chunky.txt
     *
     * @param textToAdd the text to add into the file
     * @throws IOException throws when the file is not found
     */
    public static void fileWrite(String textToAdd) throws IOException {
        try {
            String filePath = "../Chunky.txt";
            File file = new File(filePath);
            FileWriter fw = new FileWriter(filePath, true);
            fw.write(textToAdd);
            fw.close();
        } catch (IOException e) {
            System.out.println("Error creating file!");
            throw e;
        }
    }

    public static void main(String[] args) throws MissingArgumentException, InvalidMessageException, IOException {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("----------------------------\n");
        System.out.println("Hello! I'm Chunky!\n" + logo);
        System.out.println("What can I do for you ?\n");
        System.out.println("----------------------------\n");
        System.out.println("Bye! Hope to see you again soon!");
        System.out.println("----------------------------\n");
        Scanner sc = new Scanner(System.in);
        System.out.println("Say hi to Chunky :)\n");

        String txt = sc.nextLine();
        while (!txt.trim().equalsIgnoreCase("bye")) {

            System.out.println("____________________________________________________________");
            if (txt.startsWith("mark ")) {
                try {
                    String str = txt.substring(5);
                    if (str.isEmpty()) {
                        throw new MissingArgumentException("delete description cannot be empty!");
                    }
                    int taskNum = Integer.parseInt(str) - 1;
                    if (taskNum >= tasks.size()) {
                        throw new InvalidMessageException("Invalid task number!");
                    }
                    if (taskNum >= 0 && taskNum < tasks.size()) {
                        tasks.get(taskNum).markAsDone();
                        System.out.println(" Nice! I've marked this task as done:");
                        System.out.println("   " + tasks.get(taskNum));
                    }
                } catch (ChunkyException e) {
                    System.out.println(e);;
                }

            } else if (txt.startsWith("unmark ")) {
                try {
                    String str = txt.substring(7);
                    if (str.isEmpty()) {
                        throw new MissingArgumentException("delete description cannot be empty!");
                    }
                    int taskNum = Integer.parseInt(str) - 1;
                    if (taskNum >= tasks.size()) {
                        throw new InvalidMessageException("Invalid task number!");
                    }
                    if (taskNum >= 0 && taskNum < tasks.size()) {
                        tasks.get(taskNum).unmarkDone();
                        System.out.println(" Okay! Unmarked the task:");
                        System.out.println("   " + tasks.get(taskNum));
                    }
                } catch (ChunkyException e) {
                    System.out.println(e);
                }

            } else if (txt.startsWith("delete ")) {
                try {
                    String str = txt.substring(7);
                    if (str.isEmpty()) {
                        throw new MissingArgumentException("delete description cannot be empty!");
                    }
                    int taskNum = Integer.parseInt(str) - 1;
                    if (taskNum >= tasks.size()) {
                        throw new InvalidMessageException("Invalid task number!");
                    }
                    Task t = tasks.get(taskNum);
                    System.out.println("Okay! removed " + t);
                    tasks.remove(taskNum);
                } catch (ChunkyException e) {
                    System.out.println(e);
                }
            } else if (txt.equals("list")) {
                System.out.println(" Here are the tasks in your list:");
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println(" " + (i + 1) + "." + tasks.get(i));
                }
            } else {
                try {
                    if (txt.trim().isEmpty()) {
                        throw new MissingArgumentException("Cant send nothing to Chunky");
                    }
                    if (txt.startsWith("event")) {
                        if (txt.length() == 4) {
                            throw new MissingArgumentException("Event description cannot be empty!");
                        }
                        String str = txt.substring(6);
                        if (str.isEmpty()) {
                            throw new MissingArgumentException("Event description cannot be empty!");
                        }
                        String[] part = str.split(" /from | /to");
                        if (part.length != 3) {
                            throw new InvalidMessageException(
                                    "Event format should be: event <description> /from <start> /to <end>");
                        }
                        String des = part[0];
                        String start = part[1];
                        String end = part[2];
                        Events e = new Events(des, start, end);
                        tasks.add(e);
                        System.out.println("Got it. I've added this task:");
                        System.out.println(e);
                    } else if (txt.startsWith("deadline")) {
                        if (txt.length() == 8) {
                            throw new MissingArgumentException("Deadline description cannot be empty!");
                        }
                        String str = txt.substring(9);
                        if (str.isEmpty()) {
                            throw new MissingArgumentException("Deadline description cannot be empty!");
                        }
                        String[] part = str.split(" /by ");
                        if (part.length != 2) {
                            throw new InvalidMessageException(
                                    "Event format should be: event <description> /by <date>");
                        }
                        String des = part[0];
                        String by = part[1];
                        Deadlines d = new Deadlines(des, by);
                        tasks.add(d);
                        System.out.println("Got it. I've added this task:");
                        System.out.println(d);
                    } else if (txt.startsWith("todo")) {
                        if (txt.length() == 4) {
                            throw new MissingArgumentException("Todo description cannot be empty!");
                        }
                        String str = txt.substring(5);
                        if (str.isEmpty()) {
                            throw new MissingArgumentException("Todo description cannot be empty!");
                        }
                        ToDo toDo = new ToDo(str);
                        tasks.add(toDo);
                        System.out.println("Got it. I've added this task:");
                        System.out.println(toDo);
                    }
                } catch (ChunkyException e) {
                    System.out.println(e);
                }
            }
            System.out.println("Now you have " + tasks.size() + " tasks in the list.");
            System.out.println("____________________________________________________________");
            txt = sc.nextLine();
        }
        for (int i = 0; i < tasks.size(); i++) {
            textToAdd += " " + (i + 1) + "." + tasks.get(i) + "\n";
        }
        fileWrite(textToAdd);
        System.out.println("----------------------------\n");
        System.out.println(txt + " See ya!");
        System.out.println("----------------------------\n");
        sc.close();
    }
}
