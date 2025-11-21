package ui.actions;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

import model.MindMap;
import model.Note;
import ui.MindMapUI;

public class MouseAction extends MouseAdapter {
    private List<Note> children;
    private MindMapUI network;
    private MindMap mindMap;
    private Ellipse2D mainCircle;
    private List<Ellipse2D> childCircles = new ArrayList<>();

    public MouseAction(Ellipse2D mainCircle, List<Ellipse2D> childCircle, List<Note> children, MindMapUI network, MindMap mindMap) {
        this.mainCircle = mainCircle;
        this.childCircles = childCircle;
        this.children = children;
        this.network = network;
        this.mindMap = mindMap;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        handleClick(e.getX(), e.getY(), e);
    }

    private void handleClick(int mx, int my, MouseEvent e) {
        if (mainCircle != null && mainCircle.contains(mx, my)) {
            System.out.println("Clicked MAIN circle");
            handleClickMain(e);
        } else {
            for (int i = 0; i < childCircles.size(); i++) {
                if (childCircles.get(i).contains(mx, my)) {
                    System.out.println("Clicked CHILD #" + i);
                    handleClickChild(e, i);
                    break;
                }
            }
        }
    }

    private void handleClickMain(MouseEvent e) {
        if (e.isShiftDown() && (mindMap.getMovingNote() != null) && children.size() < 15) {
            System.out.println("Shift + Main Circle");
            mindMap.moveNote();
        } else if (e.isControlDown() && children.size() < 15) {
            System.out.println("Ctrl + Main Circle");
            mindMap.getSelected().addChild(new Note("..."));
        } else {
            System.out.println("Normal click on Main Circle");
            // edit
        }
        network.repaint();
    }

    private void handleClickChild(MouseEvent e, int index) {
        if (e.isShiftDown()) {
            System.out.println("Shift + Child " + index);
            mindMap.setMovingNote(mindMap.delChildOfSelected(index));
        } else if (e.isControlDown()) {
            System.out.println("Ctrl + Child " + index);
            mindMap.setSelected(children.get(index));
        } else {
            System.out.println("Normal click on Child " + index);
            // edit
        }
        network.repaint();
    }
}
