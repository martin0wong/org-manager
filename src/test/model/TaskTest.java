package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {
    private Task task1;

    @BeforeEach
    void runBefore() {
        task1 = new Task("Task 1");
    }

    @Test
    void setNameTest() {
        assertEquals("Task 1", task1.getName());
        task1.setName("Important Task!");
        assertEquals("Important Task!", task1.getName());
    }
}
