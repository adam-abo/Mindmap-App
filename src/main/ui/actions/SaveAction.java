package ui.actions;

import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import javax.swing.*;
import model.MindMap;
import persistence.JsonWriter;

public class SaveAction extends AbstractAction {
    private MindMap mindMap;
    private JsonWriter jsonWriter;
    private static final String JSON_STORE = "./data/mindmap.json";

    public SaveAction(MindMap mindMap) {
        this.mindMap = mindMap;
        jsonWriter = new JsonWriter(JSON_STORE);
    }

    // MODIFIES: this
    // EFFECTS: loads mindmap from file.
    // mindMap.getSelected() and mindMap.getgetMovingNote() will be reset, as it
    // would be somewhat
    // strange for the user to have their values persist
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            jsonWriter.open();
            jsonWriter.write(mindMap);
            jsonWriter.close();
            System.out.println("Saved " + mindMap.getContent() + " to " + JSON_STORE);
        } catch (FileNotFoundException fnf) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }
}