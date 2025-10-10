package model;

import java.util.ArrayList;
import java.util.List;

public abstract class Parent {
    protected String content;
    protected List<Node> children;
    protected List<Parent> path;

    // EFFECTS: constructs a Parent with the given content and path.
    // The list of children will be empty.
    public Parent(String content, List<Parent> path) {
        children = new ArrayList<>();

        this.content = content;
        setPath(path);
        this.path.add(this);
    }

    // EFFECTS: constructs a Parent with no content.
    // The list of children and path will be empty.
    public Parent() {
        children = new ArrayList<>();
        path = new ArrayList<>();
        this.path.add(this);
    }

    // MODIFIES: this
    // EFFECTS: constructs and adds a sub-node that connects to this parent
    public void constructChild(String content) {
        Node child = new Node(content, path);
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

    public List<Node> getChildren() {
        return children;
    }

    public List<Parent> getPath(){
        return path;
    }

    public String getContent() {
        return content;
    }

    public void setPath(List<Parent> path){
        this.path = new ArrayList<>(path);
    }

    public void setContent(String content) {
        this.content = content;
    }
}