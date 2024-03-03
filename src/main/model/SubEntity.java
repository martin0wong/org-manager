package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.LinkedList;

// class for SubEntities which can be assigned tasks
public class SubEntity implements Writable {
    private String entityName;
    private int headCount;
    private LinkedList<Task> taskList;

    // EFFECTS: construct a subEntity with a name and headcount
    public SubEntity(String name, int count) {
        entityName = name;
        headCount = count;
        taskList = new LinkedList<>();
    }

    // MODIFIES: this
    // EFFECTS: add a task for the sub-entity
    public void addTask(Task task) {
        taskList.addLast(task);
    }

    // MODIFIES: this
    // EFFECTS: the sub-entity does a task from the queue
    public void doTask() {
        if (taskList.size() != 0) {
            taskList.removeFirst();
        }
    }

    // SETTERS
    public void setName(String name) {
        entityName = name;
    }

    public void setHeadCount(int newCount) {
        headCount = newCount;
    }

    // GETTERS
    public String getName() {
        return entityName;
    }

    public int getHeadCount() {
        return headCount;
    }

    public LinkedList<Task> getTaskList() {
        return taskList;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("subOrgName", entityName);
        json.put("headCount", headCount);
        json.put("tasks", tasksToJson());
        return json;
    }

    // EFFECTS: returns things in this subEntity as a JSON array
    private JSONArray tasksToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Task t : taskList) {
            jsonArray.put(t.toJson());
        }
        return jsonArray;
    }
}
