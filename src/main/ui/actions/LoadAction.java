package ui.actions;

import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.*;
import model.MindMap;
import persistence.JsonReader;
import ui.MindMapUI;

public class LoadAction extends AbstractAction {
    private MindMap mindMap;
    private MindMapUI network;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/mindmap.json";

    public LoadAction(MindMap mindMap, MindMapUI network) {
        this.mindMap = mindMap;
        jsonReader = new JsonReader(JSON_STORE);
        this.network = network;
    }

    // MODIFIES: this
    // EFFECTS: loads mindmap from file.
    // mindMap.getSelected() and mindMap.getgetMovingNote() will be reset, as it
    // would be somewhat strange for the user to have their values persist
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            mindMap = jsonReader.read();
            System.out.println("Loaded " + mindMap.getContent() + " from " + JSON_STORE);
        } catch (IOException ioe) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        } catch (org.json.JSONException jsone) {
            System.out.println("No saved data to load");
        }
        network.repaint();
    }
}