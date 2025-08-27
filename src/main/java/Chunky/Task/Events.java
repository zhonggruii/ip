package main.java.Chunky.Task;

public class Events extends Task {
    private String startTime;
    private String endTime;

    public Events(String description, String startTime, String endTime) {
        super(description);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "[E] " + super.toString() +
                String.format(" (from: %s to: %s)", super.formatDate(this.startTime),
                        super.formatDate(this.endTime));
    }

    public String getFrom() {
        return this.startTime;
    }

    public String getTo() {
        return this.endTime;
    }
}
