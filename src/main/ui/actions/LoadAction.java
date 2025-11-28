package ui.actions;

import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.*;
import persistence.JsonReader;
import ui.MindMapUI;

// Action class for pressing the load button
public class LoadAction extends AbstractAction {
    private MindMapUI network;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/mindmap.json";

    public LoadAction(MindMapUI network) {
        jsonReader = new JsonReader(JSON_STORE);
        this.network = network;
    }

    // MODIFIES: network
    // EFFECTS: loads mindmap from file.
    // mindMap.getSelected() and mindMap.getgetMovingNote() will be reset
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            network.setMindMap(jsonReader.read());
        } catch (IOException ioe) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        } catch (org.json.JSONException jsone) {
            System.out.println("No saved data to load");
        }
        network.repaint();
    }
}