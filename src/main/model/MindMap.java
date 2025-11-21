package model;

import java.util.List;

/*  
* This class is responsible for interacting all the nodes of the mindmap,
* as well as being the root node for all child nodes. 
* It  also keeps track of the current selected node.
* It extends Parent's behaviour of having children.
*/

public class MindMap extends Node {
    private Node selected;
    private Note movingNote;

    public MindMap() {
        super();
        selected = this;
    }

    // MODIFIES: this
    // EFFECTS: adds a child with inputted user content to the selected node
    public void constructChildOfSelected(String content) {
        selected.constructChild(content);
    }

    // MODIFIES: this, Note
    // EFFECTS: deletes a child of the currently selected node
    public Note delChildOfSelected(int index) {
        return selected.deleteChild(index);
    }

    // REQUIRES: movingNote is not null
    // MODIFIES: this, Note
    // EFFECTS: attatches the movingNote to the current selected Note,
    // configures the path of movingNote and its children to account for the move
    public void moveNote() {
        selected.addChild(movingNote);
        movingNote = null;
    }

    // MODIFIES: this, Note
    // EFFECTS: selects a sub-node via user inputted index
    public void selectChildOfSelected(int index) {
        selected = selected.getChild(index);
    }

    // REQUIRES: selected.getPath().size() > 1
    // MODIFIES: this
    // EFFECTS: selects the parent of the current selected node if possible
    public void selectParentOfSelected() {
        List<Node> path = selected.getPath();
        if (path.size() > 1){
            selected = path.get(path.size() - 2);
        }
    }

    public void setMovingNote(Note note) {
        movingNote = note;
    }

    public void setSelected(Node selected) {
        this.selected = selected;
    }

    public Note getMovingNote() {
        return movingNote;
    }

    public Node getSelected() {
        return selected;
    }
}
