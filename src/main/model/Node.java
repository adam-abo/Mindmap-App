package model;

import java.util.ArrayList;
import java.util.List;

public abstract class Node {
    protected String content;
    protected List<Note> children;
    protected List<Node> path;

    // EFFECTS: constructs a Parent with the given content and path.
    // The list of children will be empty.
    public Node(String content, List<Node> path) {
        children = new ArrayList<>();

        this.content = content;
        setPath(path);
        this.path.add(this);
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
        Note child = new Note(content, path);
        children.add(child);
        child.setId(children.size() - 1);
    }

    // MODIFIES: this
    // EFFECTS: deletes a child of this parent and all of the child's children
    public void deleteChild(int index) {
        children.remove(index);
        for (int i = index; i < children.size(); i++) {
            children.get(index).setId(index);
        }
    }

    // EFFECTS: returns the child at the given index
    public Node getChild(int index) {
        return children.get(index);
    }

    public List<Note> getChildren() {
        return children;
    }

    public List<Node> getPath(){
        return path;
    }

    public String getContent() {
        return content;
    }

    public void setPath(List<Node> path){
        this.path = new ArrayList<>(path);
    }

    public void setContent(String content) {
        this.content = content;
    }
}