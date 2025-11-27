package persistence;

import model.Note;
import model.Node;
import model.Event;
import model.EventLog;
import model.MindMap;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Citation: this class is a modification of the JsonReader class from JsonSerializationDemo
// Represents a reader that reads mindmap from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads mindmap from file and returns it;
    // throws IOException if an error occurs reading data from file
    public MindMap read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);

        EventLog.getInstance().logEvent(new Event("Loaded from " + source));
        return parseMindMap(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses mindmap from JSON object and returns it
    private MindMap parseMindMap(JSONObject jsonObject) {
        String name = jsonObject.getString("content");
        MindMap mm = new MindMap();
        mm.setContent(name);
        addNotes(mm, jsonObject);
        return mm;
    }

    // MODIFIES: node
    // EFFECTS: parses notes from JSON object and adds them to mindmap
    private void addNotes(Node node, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("notes");
        for (Object json : jsonArray) {
            JSONObject nextNode = (JSONObject) json;
            addNote(node, nextNode);
        }
    }

    // MODIFIES: node
    // EFFECTS: parses note from JSON object and adds it to mindmap
    private void addNote(Node node, JSONObject jsonObject) {
        String content = jsonObject.getString("content");
        Note note = new Note(content);
        node.addChild(note);
        addNotes(note, jsonObject);
    }
}
