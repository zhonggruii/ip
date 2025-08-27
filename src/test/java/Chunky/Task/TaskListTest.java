package test.java.Chunky.Task;

import main.java.Chunky.Task.TaskList;
import main.java.Chunky.Task.ToDo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskListTest {
    private TaskList taskList;
    private ToDo todo;

    @Test
    void testEmptyTaskListSize() {
        taskList = new TaskList();
        assertEquals(0, taskList.size());
    }

    @Test
    void testAddTask() {
        taskList = new TaskList();
        todo = new ToDo("read book");
        taskList.add(todo);
        assertEquals(1, taskList.size());
        assertEquals(todo, taskList.get(0));
    }
}