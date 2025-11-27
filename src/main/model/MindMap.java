package model;

import java.util.List;

/*  
* This class is responsible for interacting all the nodes of the mindmap,
* as well as being the root node for all child nodes. 
* It  also keeps track of the current selected node.
* It extends Parent's behaviour of having children.
* All events that happen are logged.
*/

public class MindMap extends Node {
    private Node selected;
    private Note movingNote;
    private EventLog logAccess = EventLog.getInstance();

    public MindMap() {
        super();
        selected = this;
    }

    // MODIFIES: this
    // EFFECTS: adds a child with inputted user content to the selected node
    public void constructChildOfSelected(String content) {
        selected.constructChild(content);
        logAccess.logEvent(new Event("Added new child node to " + makeTag(selected)));
    }

    // MODIFIES: this, Note
    // EFFECTS: deletes a child of the currently selected node
    public Note delChildOfSelected(int index) {
        Note deleted = selected.deleteChild(index);
        logAccess.logEvent(new Event("Cut selected child node: " + makeTag(deleted)));
        return deleted;
    }

    // REQUIRES: movingNote is not null
    // MODIFIES: this, Note
    // EFFECTS: attatches the movingNote to the current selected Note,
    // configures the path of movingNote and its children to account for the move
    public void moveNote() {
        selected.addChild(movingNote);
        logAccess.logEvent(new Event("Pasted the " + makeTag(movingNote) + " child node to " + makeTag(selected)));
        movingNote = null;
    }

    // MODIFIES: this, Note
    // EFFECTS: selects a sub-node via user inputted index
    public void selectChildOfSelected(int index) {
        selected = selected.getChild(index);
        logAccess.logEvent(new Event("Selected child node: " + makeTag(selected)));
    }

    // REQUIRES: selected.getPath().size() > 1
    // MODIFIES: this
    // EFFECTS: selects the parent of the current selected node if possible
    public void selectParentOfSelected() {
        List<Node> path = selected.getPath();
        if (path.size() > 1) {
            selected = path.get(path.size() - 2);
            logAccess.logEvent(new Event("Selected parent node: " + makeTag(selected)));
        }
    }

    private String makeTag(Node node) {
        String tag;

        if (node.getContent().length() > 10) {
            tag = node.getContent().substring(0, 10) + "... ";
        } else {
            tag = node.getContent();
        }

        return tag;
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
