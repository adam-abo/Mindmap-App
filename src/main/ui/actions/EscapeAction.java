package ui.actions;

import java.awt.event.ActionEvent;

import javax.swing.*;
import model.MindMap;
import ui.MindMapUI;

public class EscapeAction extends AbstractAction {
    private MindMapUI network;
    private MindMap mindMap;

    public EscapeAction(MindMap mindMap, MindMapUI network) {
        this.network = network;
        this.mindMap = mindMap;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        mindMap.selectParentOfSelected();
        network.repaint();
    }
}
