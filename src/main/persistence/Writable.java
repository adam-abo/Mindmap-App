package persistence;

import org.json.JSONObject;

// Citation: this interface is from JsonSerializationDemo
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
