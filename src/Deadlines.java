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
}
