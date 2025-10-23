package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

public abstract class Node implements Writable {
    protected String content;
    protected List<Note> children;
    protected List<Node> path;

    // EFFECTS: constructs a Parent with the given content and path.
    // The list of children will be empty.
    public Node(String content) {
        children = new ArrayList<>();

        this.content = content;
    }

    // EFFECTS: constructs a Parent with no content.
    // The list of children and path will be empty.
    public Node() {
        children = new ArrayList<>();
        path = new ArrayList<>();
        this.path.add(this);
    }

    // MODIFIES: this
    // EFFECTS: constructs and adds a sub-node that connects to this parent
    public void constructChild(String content) {
        Note child = new Note(content);
        addChild(child);
    }

    // MODIFIES: this
    // EFFECTS: deletes a child of this parent and all of the child's children
    public Note deleteChild(int index) {
        Note deleted = children.remove(index);
        for (int i = index; i < children.size(); i++) {
            children.get(index).setId(index);
        }

        return deleted;
    }

    public void addChild(Note child) {
        children.add(child);
        child.setId(children.size() - 1);
        child.setPath(path);
        child.getPath().add(child);
        child.pathIdUpdate(child.getPath());
    }

    // EFFECTS: update all Nodes branching from a given Node to have paths built
    // from the given Node
    public void pathIdUpdate(List<Node> path) {
        for (int i = 0; i < children.size(); i++) {
            Note child = children.get(i);
            List<Node> childPath = new ArrayList<>(path);

            child.setId(i);
            childPath.add(child);
            child.setPath(childPath);
            child.pathIdUpdate(childPath);
        }
    }

    @Override
    // 
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("content", content);
        json.put("notes", notesToJson());
        return json;
    }

    // EFFECTS: returns notes under this Node as a JSON array
    private JSONArray notesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Note n : children) {
            jsonArray.put(n.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns the child at the given index
    public Note getChild(int index) {
        return children.get(index);
    }

    public List<Note> getChildren() {
        return children;
    }

    public List<Node> getPath() {
        return path;
    }

    public String getContent() {
        return content;
    }

    public void setPath(List<Node> path) {
        this.path = new ArrayList<>(path);
    }

    public void setContent(String content) {
        this.content = content;
    }
}