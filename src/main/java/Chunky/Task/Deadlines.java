package main.java.Chunky.Task;

/**
 * Deadlines class, has a deadline for each Task Object
 */
public class Deadlines extends Task {
    private String dueDate;

    public Deadlines(String description, String dueDate) {
        super(description);
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "[D] " + super.toString() + " (by: " + super.formatDate(this .dueDate) + ")";
    }

    public String getBy() {
        return this.dueDate;
    }
}
