package ui.actions;

import java.awt.event.ActionEvent;

import javax.swing.*;
import ui.MindMapUI;

// Action class for pressing the back button
public class BackAction extends AbstractAction {
    private MindMapUI network;

    public BackAction(MindMapUI network) {
        this.network = network;
    }

    // MODIFIES: network
    // EFFECTS: attempts to select the parent node of the current node
    @Override
    public void actionPerformed(ActionEvent e) {
        network.getMindMap().selectParentOfSelected();
        network.repaint();
    }
}
