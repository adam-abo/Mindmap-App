package model;

import java.util.List;

/*  
* This class is responsible for controlling all the nodes of the mindmap, as well as being the root node for all child nodes.
* It extends Parent's behaviour of having children.
*/
public class MindMap extends Node {
    private Node selected;

    public MindMap() {
        super();
        selected = this;
    }

    // MODIFIES: this, Parent
    // EFFECTS: adds a child with inputted user content to the selected node
    public void constructChildOfSelected(String content) {
        selected.constructChild(content);
    }

    // MODIFIES: this, Parent
    // EFFECTS: deletes a child of the currently selected node
    public void delChildOfSelected(int index) {
        selected.deleteChild(index);
    }

    // MODIFIES: this, Parent
    // EFFECTS: selects a sub-node via user inputted index
    public void selectChildOfSelected(int index) {
        selected = selected.getChild(index);
    }

    // REQUIRES: selected.getPath().size() > 1
    // MODIFIES: this
    // EFFECTS: selects the parent of the current selected node if possible,
    public void selectParentOfSelected() {
        List<Node> path = selected.getPath();
        selected = path.get(path.size() - 2);
    }

    public void setSelected(Node selected) {
        this.selected = selected;
    }

    public Node getSelected() {
        return selected;
    }
}
