package ui.actions;

import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import javax.swing.*;
import persistence.JsonWriter;
import ui.MindMapUI;

// Action class for pressing the save button
public class SaveAction extends AbstractAction {
    private MindMapUI network;
    private JsonWriter jsonWriter;
    private static final String JSON_STORE = "./data/mindmap.json";

    public SaveAction(MindMapUI network) {
        jsonWriter = new JsonWriter(JSON_STORE);
        this.network = network;
    }

    // MODIFIES: network
    // EFFECTS: loads mindmap from file.
    // mindMap.getSelected() and mindMap.getgetMovingNote() will be reset
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            jsonWriter.open();
            jsonWriter.write(network.getMindMap());
            jsonWriter.close();
        } catch (FileNotFoundException fnf) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }
}