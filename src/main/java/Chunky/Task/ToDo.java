package main.java.Chunky.Task;

/**
 * To Do class to create To Do objects
 */
public class ToDo extends Task {

    public ToDo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T] " + super.toString();
    }
}
