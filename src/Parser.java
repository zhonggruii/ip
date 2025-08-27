public class Parser {

    public static String getCommand(String input) {
        return input.trim().split("\\s+")[0].toLowerCase();
    }

    public static String getArguments(String input) {
        String[] parts = input.trim().split("\\s+", 2);
        return parts.length > 1 ? parts[1] : "";
    }

    public static int parseTaskIndex(String arguments) throws InvalidMessageException, MissingArgumentException {
        if (arguments.isEmpty()) {
            throw new MissingArgumentException("Task number cannot be empty!");
        }
        try {
            return Integer.parseInt(arguments.trim()) - 1;
        } catch (NumberFormatException e) {
            throw new InvalidMessageException("Task number must be a valid integer!");
        }
    }

    public static Task parseTask(String input) throws ChunkyException {
        String command = getCommand(input);
        String arguments = getArguments(input);

        switch (command) {
            case "todo":
                if (arguments.isEmpty()) {
                    throw new MissingArgumentException("Todo description cannot be empty!");
                }
                return new ToDo(arguments);

            case "deadline":
                return parseDeadline(arguments);

            case "event":
                return parseEvent(arguments);

            default:
                throw new InvalidMessageException("Unknown task type!");
        }
    }

    private static Task parseDeadline(String arguments) throws ChunkyException {
        if (arguments.isEmpty()) {
            throw new MissingArgumentException("Deadline description cannot be empty!");
        }

        String[] parts = arguments.split(" /by ", 2);
        if (parts.length != 2) {
            throw new InvalidMessageException("Deadline format should be: deadline <description> /by <date>");
        }
        validateDate(parts[1].trim());
        return new Deadlines(parts[0].trim(), parts[1].trim());
    }

    private static Task parseEvent(String arguments) throws ChunkyException {
        if (arguments.isEmpty()) {
            throw new MissingArgumentException("Event description cannot be empty!");
        }

        String[] parts = arguments.split(" /from | /to");
        if (parts.length != 3) {
            throw new InvalidMessageException("Event format should be: event <description> /from <start> /to <end>");
        }
        validateDate(parts[1].trim());
        validateDate(parts[2].trim());
        return new Events(parts[0].trim(), parts[1].trim(), parts[2].trim());
    }

    private static void validateDate(String dateInput) throws InvalidMessageException {
        String trimmed = dateInput.trim().toLowerCase();

        // Must contain at least one digit OR common date/time word
        if (!trimmed.matches(
                ".*(\\d|today|tomorrow|monday|tuesday|wednesday|thursday|friday|saturday|sunday|pm|am).*")) {
            throw new InvalidMessageException("Please provide a valid date!");
        }
    }
}

