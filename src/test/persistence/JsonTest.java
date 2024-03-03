package persistence;

import model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkSubOrg(String name, int headcount, SubEntity subOrg) {
        assertEquals(name, subOrg.getName());
        assertEquals(headcount, subOrg.getHeadCount());
    }

    protected void checkTask(String name, Task task) {
        assertEquals(name, task.getName());
    }
}