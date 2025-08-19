import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Chunky {
    public static List<Task> tasks = new ArrayList<>();

    public static void main(String[] args) {
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
            if (txt.startsWith("mark ")) {
                int taskNum = Integer.parseInt(txt.substring(5)) - 1;
                if (taskNum >= 0 && taskNum < tasks.size()) {
                    tasks.get(taskNum).markAsDone();
                    System.out.println("____________________________________________________________");
                    System.out.println(" Nice! I've marked this task as done:");
                    System.out.println("   " + tasks.get(taskNum));
                    System.out.println("____________________________________________________________");
                }
            } else if (txt.startsWith("unmark ")) {
                int taskNum = Integer.parseInt(txt.substring(7)) - 1;
                if (taskNum >= 0 && taskNum < tasks.size()) {
                    tasks.get(taskNum).unmarkDone();
                    System.out.println("____________________________________________________________");
                    System.out.println(" Okay! Unmarked the task:");
                    System.out.println("   " + tasks.get(taskNum));
                    System.out.println("____________________________________________________________");
                }
            } else if (txt.equals("list")) {
                System.out.println("____________________________________________________________");
                System.out.println(" Here are the tasks in your list:");
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println(" " + (i + 1) + "." + tasks.get(i));
                }
                System.out.println("____________________________________________________________");
            } else {
                tasks.add(new Task(txt));
                System.out.println("____________________________________________________________");
                System.out.println(" I have added " + txt + " to the list");
                System.out.println("____________________________________________________________");
            }
            txt = sc.nextLine();
        }
        System.out.println("----------------------------\n");
        System.out.println(txt + " See ya!");
        System.out.println("----------------------------\n");
        sc.close();
    }
}
