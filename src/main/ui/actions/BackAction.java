package ui.actions;

import java.awt.event.ActionEvent;

import javax.swing.*;
import ui.MindMapUI;

public class BackAction extends AbstractAction {
    private MindMapUI network;

    public BackAction(MindMapUI network) {
        this.network = network;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        network.getMindMap().selectParentOfSelected();
        network.repaint();
    }
}
