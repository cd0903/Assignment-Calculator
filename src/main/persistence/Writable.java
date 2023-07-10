package persistence;

import org.json.JSONObject;

// Code modified from JsonSerializationDemo

public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
