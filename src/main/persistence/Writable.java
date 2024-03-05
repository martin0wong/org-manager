package persistence;

import org.json.JSONObject;

// CITATION: similar to the demo given
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
