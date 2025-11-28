package ui.actions;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import model.MindMap;
import model.Note;
import model.Node;
import ui.MindMapUI;

// This class represents the actions that occur due to mouse clicks on the Mindmap frame
public class MindMapMouseAction extends MouseAdapter {
    private MindMapUI network;
    private Ellipse2D mainCircle;
    private List<Ellipse2D> childCircles = new ArrayList<>();

    public MindMapMouseAction(MindMapUI network) {
        this.network = network;
    }

    // EFFECTS: detects a mouseclick and sends needed info to be handled
    @Override
    public void mouseClicked(MouseEvent e) {
        this.mainCircle = network.getMainCircle();
        this.childCircles = network.getChildCircles();
        handleClick(e.getX(), e.getY(), e);
    }

    // EFFECTS: decides which node was clicked (if any) and handles it appropriately
    private void handleClick(int mx, int my, MouseEvent e) {
        List<Note> children = network.getMindMap().getSelected().getChildren();
        MindMap mindMap = network.getMindMap();
        if (mainCircle != null && mainCircle.contains(mx, my)) {
            handleClickMain(e, mindMap, children);
        } else {
            for (int i = 0; i < childCircles.size(); i++) {
                if (childCircles.get(i).contains(mx, my)) {
                    handleClickChild(e, i, mindMap, children);
                    break;
                }
            }
        }
    }

    // MODIFIES: mindMap, network
    // EFFECTS: handles the case of a click on the main node
    // - if it's a shift-click, then attempt to paste a node as a new child to the
    // main node
    // - if it's a ctrl-click, then add a new child to the main node (max is 15)
    // - otherwise, ask the user to edit the content of the node
    private void handleClickMain(MouseEvent e, MindMap mindMap, List<Note> children) {
        if (e.isShiftDown() && (mindMap.getMovingNote() != null) && children.size() < 15) {
            mindMap.moveNote();
        } else if (e.isControlDown() && children.size() < 15) {
            mindMap.constructChildOfSelected("...");
        } else {
            editNode(mindMap.getSelected());
        }
        network.repaint();
    }

    // MODIFIES: mindMap, network
    // EFFECTS: handles the case of a click on a child node
    // - if it's a shift-click, then cut that node and save it for pasting later
    // - if it's a ctrl-click, then select and expand that node
    // - otherwise, ask the user to edit the content of the node
    private void handleClickChild(MouseEvent e, int index, MindMap mindMap, List<Note> children) {
        if (e.isShiftDown()) {
            mindMap.setMovingNote(mindMap.delChildOfSelected(index));
        } else if (e.isControlDown()) {
            mindMap.setSelected(children.get(index));
        } else {
            editNode(mindMap.getSelected().getChild(index));
        }
        network.repaint();
    }

    // MODIFIES: node
    // EFFECTS: opens a text box for the user to edit the content of a given node
    private void editNode(Node node) {
        String newText = JOptionPane.showInputDialog(network, "Edit text:", node.getContent());
        if (newText != null) {
            node.setContent(newText);
        }
    }
}
