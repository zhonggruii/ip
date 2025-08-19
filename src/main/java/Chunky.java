package main.java;

import java.util.Scanner;

public class Chunky {
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
        while (!txt.equals("bye")) {
            System.out.println("----------------------------\n");
            System.out.println(txt);
            System.out.println("----------------------------\n");
            txt = sc.nextLine();
        }
        System.out.println("----------------------------\n");
        System.out.println(txt + " See ya!");
        System.out.println("----------------------------\n");
        sc.close();
    }
}
