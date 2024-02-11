package model;

// class for representing tasks for sub-entities
public class Task {
    private String taskName;

    // EFFECTS: construct a task with a name
    public Task(String name) {
        taskName = name;
    }

    // SETTERS
    public void setName(String name) {
        taskName = name;
    }

    // GETTERS
    public String getName() {
        return taskName;
    }
}
