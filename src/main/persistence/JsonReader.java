package persistence;

import model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import org.json.*;

// Represents a reader that reads OrgEntity from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads OrgEntity from file and returns it;
    // throws IOException if an error occurs reading data from file
    public OrgEntity read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseOrgEntity(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses OrgEntity from JSON object and returns it
    private OrgEntity parseOrgEntity(JSONObject jsonObject) {
        String name = jsonObject.getString("orgName");
        OrgEntity org = new OrgEntity(name);
        addSubOrgs(org, jsonObject);
        return org;
    }

    // MODIFIES: org
    // EFFECTS: parses SubEntities from JSON object and adds them to OrgEntity
    private void addSubOrgs(OrgEntity org, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("subOrgs");
        for (Object json : jsonArray) {
            JSONObject nextSubOrg = (JSONObject) json;
            addSubOrg(org, nextSubOrg);
        }
    }

    // MODIFIES: org
    // EFFECTS: parses SubEntity from JSON object and adds it to OrgEntity
    private void addSubOrg(OrgEntity org, JSONObject jsonObject) {
        String name = jsonObject.getString("subOrgName");
        int headCount = jsonObject.getInt("headCount");
        SubEntity subOrg = new SubEntity(name, headCount);

        addTasks(subOrg, jsonObject);

        org.addSubEntity(subOrg);
    }

    // MODIFIES: subOrg
    // EFFECTS: parses Tasks from JSON object and adds it to SubEntity
    private void addTasks(SubEntity subOrg, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("tasks");
        for (Object json : jsonArray) {
            JSONObject nextSubOrg = (JSONObject) json;
            addTask(subOrg, nextSubOrg);
        }
    }

    // MODIFIES: subOrg
    // EFFECTS: parses task from JSON object and adds it to SubEntity
    private void addTask(SubEntity subOrg, JSONObject jsonObject) {
        String taskName = jsonObject.getString("taskName");
        Task task = new Task(taskName);
        subOrg.addTask(task);
    }
}