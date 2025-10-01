package model;

import java.util.List;

/*
The Node class represents the nodes of the mind map, with each Node having a text note.
Each node can have sub-nodes added to it, which are listed as children.
A Node can be deleted (along with its children), as well have its note edited.
*/

public class Node {
    String note;
    List<Node> children;

    // EFFECTS: constructs a Node with the given note as the content of the node. The list of sub-nodes will be empty.
    public Node(String note) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: edits the content of the node's note
    private void editNote() {

    }

    // MODIFIES: this
    // EFFECTS: adds a sub-node that connects to this node
    private void addNode() {

    }

    // MODIFIES: this
    // EFFECTS: deletes this node and all of its sub-nodes
    private void delete() {

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
