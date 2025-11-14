package ui;

import javax.swing.*;

import model.MindMap;
import model.Node;
import model.Note;

import java.util.List;
import java.util.ArrayList;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

public class NodeNetwork extends JPanel {

    private Node node;
    private List<Note> children;
    private MindMap mindMap;

    // hit-boxes for clicks
    private Ellipse2D mainCircle;
    private List<Ellipse2D> childCircles = new ArrayList<>();

    public NodeNetwork(MindMap mindMap) {
        super();
        this.mindMap = mindMap;

        // set up mouse detection
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                handleClick(e.getX(), e.getY(), e);
            }
        });
    }

    private void handleClick(int mx, int my, java.awt.event.MouseEvent e) {
        if (mainCircle != null && mainCircle.contains(mx, my)) {
            System.out.println("Clicked MAIN circle");

            int mods = e.getModifiersEx();
            if ((mods & java.awt.event.MouseEvent.SHIFT_DOWN_MASK) != 0 && (mindMap.getMovingNote() != null)) {
                System.out.println("Shift + Main Circle");
                mindMap.moveNote();
            } else if ((mods & java.awt.event.MouseEvent.CTRL_DOWN_MASK) != 0) {
                System.out.println("Ctrl + Main Circle");
                mindMap.getSelected().addChild(new Note("A"));
            } else
                System.out.println("Normal click on Main Circle");
                // edit
            return;
        }

        for (int i = 0; i < childCircles.size(); i++) {
            if (childCircles.get(i).contains(mx, my)) {
                System.out.println("Clicked CHILD #" + i);

                int mods = e.getModifiersEx();
                if ((mods & java.awt.event.MouseEvent.SHIFT_DOWN_MASK) != 0) {
                    System.out.println("Shift + Child " + i);
                    mindMap.setMovingNote(mindMap.delChildOfSelected(i));
                } else if ((mods & java.awt.event.MouseEvent.CTRL_DOWN_MASK) != 0) {
                    System.out.println("Ctrl + Child " + i);
                    mindMap.setSelected(children.get(i));
                } else
                    System.out.println("Normal click on Child " + i);
                return;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        setNode();
        super.paintComponent(g);
        if (node == null)
            return;

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        childCircles.clear();

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int bigRadius = 70;

        // draw main circle
        mainCircle = new Ellipse2D.Double(centerX - bigRadius, centerY - bigRadius,
                2 * bigRadius, 2 * bigRadius);

        g2.setColor(new Color(90, 150, 255));
        g2.fill(mainCircle);

        g2.setColor(Color.BLACK);
        g2.draw(mainCircle);

        drawCenteredString(g2, node.getContent(), centerX, centerY);

        int smallRadius = 40;
        int numChildren = children.size();
        double angleStep = 2 * Math.PI / numChildren;
        int distance = 180;

        for (int i = 0; i < numChildren; i++) {
            double angle = i * angleStep;

            int childX = (int) (centerX + distance * Math.cos(angle));
            int childY = (int) (centerY + distance * Math.sin(angle));

            // line from big circle to child
            g2.draw(new Line2D.Double(centerX + bigRadius * Math.cos(angle),
                    centerY + bigRadius * Math.sin(angle),
                    childX, childY));

            // child circle
            Ellipse2D childCircle = new Ellipse2D.Double(childX - smallRadius, childY - smallRadius,
                    2 * smallRadius, 2 * smallRadius);
            childCircles.add(childCircle);

            g2.setColor(new Color(255, 200, 90));
            g2.fill(childCircle);

            g2.setColor(Color.BLACK);
            g2.draw(childCircle);

            drawCenteredString(g2, node.getChild(i).getContent(), childX, childY);
        }
    }

    private void drawCenteredString(Graphics2D g2, String text, int x, int y) {
        FontMetrics fm = g2.getFontMetrics();
        int w = fm.stringWidth(text);
        int h = fm.getAscent();
        g2.setColor(Color.BLACK);
        g2.drawString(text, x - w / 2, y + h / 4);
    }

    public void setNode() {
        this.node = mindMap.getSelected();
        this.children = node.getChildren();
        repaint();
    }
}
