package model;

import java.util.ArrayList;
import java.util.List;

/*
The Node class represents the nodes of the mind map, with each Node having a text note.
Each node can have sub-nodes added to it, which are listed as children.
A Node can be deleted (along with its children), as well have its note edited.
*/

public class Node {
    private String note;
    private List<Node> children;

    // EFFECTS: constructs a Node with the given note as the content of the node.
    // The list of sub-nodes will be empty.
    public Node(String note) {
        children = new ArrayList<>();
        this.note = note;
    }

    // MODIFIES: this
    // EFFECTS: adds a sub-node that connects to this node
    public void addChild(Node childNode) {
        children.add(childNode);
    }

    // MODIFIES: this
    // EFFECTS: constructs and adds a sub-node that connects to this node
    public void constructChild(String note) {
        children.add(new Node(note));
    }

    // MODIFIES: this
    // EFFECTS: deletes a child of this node and all of its sub-nodes
    public void deleteChild(int index) {
        children.remove(index);
    }

    public List<Node> getChildren() {
        return children;
    }

    public String getNote() {
        return note;
    }

    public void setnote(String note) {
        this.note = note;
    }
}
