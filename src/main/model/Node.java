package model;

import java.util.List;

/*
The Node class represents the nodes of the mind map, with each Node having a text note.
Each node can have sub-nodes added to it, which are listed as children.
Each node has an Id and a list of its ancestors.
A Node can be deleted (along with its children), as well have its note edited.
*/

public class Node extends Parent {
    private int id;

    // EFFECTS: constructs a Node with the given note as the content of the node.
    // The list of sub-nodes will be empty.
    public Node(String note, List<Parent> path) {
        super(note, path);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
