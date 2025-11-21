package ui;

import javax.swing.*;

import model.MindMap;
import model.Node;
import model.Note;
import ui.actions.MouseAction;

import java.util.List;
import java.util.ArrayList;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

public class MindMapUI extends JPanel {

    private Node node;
    private List<Note> children;
    private MindMap mindMap;
    private Ellipse2D mainCircle;
    private List<Ellipse2D> childCircles = new ArrayList<>();

    public MindMapUI(MindMap mindMap) {
        super();
        this.mindMap = mindMap;
        setFocusable(true);

        addMouseListener(new MouseAction(mainCircle, childCircles, children, this, mindMap));
    }

    @Override
    protected void paintComponent(Graphics g) {
        setNode();
        super.paintComponent(g);
        childCircles.clear();

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int bigRadius = 70;
        int smallRadius = 40;
        double angleStep = 2 * Math.PI / children.size();
        int distance = 180;

        drawMainCircle(g2, centerX - bigRadius, centerY - bigRadius, bigRadius, node.getContent());

        for (int i = 0; i < children.size(); i++) {
            double angle = i * angleStep;

            int childX = (int) (centerX + distance * Math.cos(angle));
            int childY = (int) (centerY + distance * Math.sin(angle));

            // line from big circle to child
            g2.draw(new Line2D.Double(centerX + bigRadius * Math.cos(angle),
                    centerY + bigRadius * Math.sin(angle), childX, childY));

            // child circle
            drawChildren(g2, childX - smallRadius, childY - smallRadius, smallRadius, node.getContent());
        }
    }

    // EFFECTS: draws a circle
    private void drawMainCircle(Graphics2D g2, int centerX, int centerY, int radius, String text) {
        mainCircle = new Ellipse2D.Double(centerX, centerY, 2 * radius, 2 * radius);

        g2.setColor(new Color(90, 150, 255));
        g2.fill(mainCircle);

        g2.setColor(Color.BLACK);
        g2.draw(mainCircle);

        drawCenteredString(g2, text, centerX, centerY);
    }

    private void drawChildren(Graphics2D g2, int centerX, int centerY, int radius, String text) {
        Ellipse2D childCircle = new Ellipse2D.Double(centerX, centerY, 2 * radius, 2 * radius);
        childCircles.add(childCircle);

        g2.setColor(new Color(255, 200, 90));
        g2.fill(childCircle);

        g2.setColor(Color.BLACK);
        g2.draw(childCircle);

        drawCenteredString(g2, text, centerX, centerY);
    }

    private void drawCenteredString(Graphics2D g2, String text, int x, int y) {
        FontMetrics fm = g2.getFontMetrics();
        int w = fm.stringWidth(text);
        int h = fm.getAscent();
        g2.setColor(Color.BLACK);
        g2.drawString(text, x - w / 2, y + h / 4);
    }

    // MODIFIES: this
    // EFFECTS: sets the current main node as the current selected node
    public void setNode() {
        this.node = mindMap.getSelected();
        this.children = node.getChildren();
    }

    public void setMindMap(MindMap mindMap) {
        this.mindMap = mindMap;
    }
}
