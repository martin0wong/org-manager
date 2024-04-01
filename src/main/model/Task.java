package model;

import org.json.JSONObject;
import persistence.Writable;

// class for representing tasks for sub-entities
public class Task implements Writable {
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

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("taskName", taskName);
        EventLog.getInstance().logEvent(new Event("Task: " + taskName + " saved to JSON"));
        return json;
    }
}
