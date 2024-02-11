package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubEntityTest {
    private SubEntity academic;
    private Task task1;
    private Task task2;

    @BeforeEach
    void runBefore() {
        academic = new SubEntity("Academic", 5);
        task1 = new Task("Test Task1");
        task2 = new Task("Test Task2");
    }

    @Test
    void addTaskTest() {
        assertEquals(0, academic.getTaskList().size());
        academic.addTask(task1);
        assertEquals(1, academic.getTaskList().size());
        academic.addTask(task2);
        assertEquals(2, academic.getTaskList().size());
    }

    @Test
    void doTaskTest() {
        academic.addTask(task1);
        academic.addTask(task2);
        assertEquals(2, academic.getTaskList().size());
        academic.doTask();
        assertEquals(1, academic.getTaskList().size());
        academic.doTask();
        assertEquals(0, academic.getTaskList().size());
        academic.doTask();
        assertEquals(0, academic.getTaskList().size());
    }

    @Test
    void setNameTest() {
        assertEquals("Academic", academic.getName());
        academic.setName("New Branch");
        assertEquals("New Branch", academic.getName());
    }

    @Test
    void getHeadCount() {
        assertEquals(5, academic.getHeadCount());
        academic.setHeadCount(10);
        assertEquals(10, academic.getHeadCount());
    }
}