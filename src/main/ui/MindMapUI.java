package ui;

import javax.swing.*;

import model.MindMap;
import model.Node;
import model.Note;
import ui.actions.MindMapMouseAction;

import java.util.List;
import java.util.ArrayList;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

/*
 * This class handles ui of the mindmap network. It also contains the data of the mindmap structure.
 * Other classes that relate to ui, such as actions, usually contain a reference to this class. 
 * However, they cannot directly change any of the fields of this class.
 */
public class MindMapUI extends JPanel {

    private Node node;
    private List<Note> children;
    private MindMap mindMap;
    private Ellipse2D mainCircle;
    private List<Ellipse2D> childCircles = new ArrayList<>();

    public MindMapUI() {
        this.mindMap = new MindMap();
        setFocusable(true);

        addMouseListener(new MindMapMouseAction(this));
    }

    // MODIFIES: this
    // EFFECTS:
    @Override
    protected void paintComponent(Graphics g) {
        updateNode();
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int bigRadius = 70;
        int smallRadius = 40;
        int distance = 180;
        double angleStep = 2 * Math.PI / children.size();

        // main circle
        mainCircle = drawCircle(g2, new Color(90, 150, 255), centerX - bigRadius, centerY - bigRadius, bigRadius,
                node.getContent());

        for (int i = 0; i < children.size(); i++) {
            double angle = i * angleStep;

            int childX = (int) (centerX + distance * Math.cos(angle));
            int childY = (int) (centerY + distance * Math.sin(angle));

            // line from big circle to child
            g2.draw(new Line2D.Double(centerX + bigRadius * Math.cos(angle),
                    centerY + bigRadius * Math.sin(angle), childX, childY));

            // child circle
            childCircles.add(drawCircle(g2, new Color(255, 200, 90), childX - smallRadius, childY - smallRadius,
                    smallRadius, node.getChild(i).getContent()));
        }
    }

    // EFFECTS: draws and returns a circle with the given arguments
    private Ellipse2D drawCircle(Graphics2D g2, Color color, int centerX, int centerY, int radius, String text) {
        Ellipse2D childCircle = new Ellipse2D.Double(centerX, centerY, 2 * radius, 2 * radius);

        g2.setColor(color);
        g2.fill(childCircle);

        g2.setColor(Color.BLACK);
        g2.draw(childCircle);

        drawCenteredString(g2, text, centerX + radius, centerY + radius, radius * 2 - 10);
        return childCircle;
    }

    // EFFECTS: draws the text inside each node centered to make the most of the
    // space. Note that if there is too much text, then it will escape the node.
    private void drawCenteredString(Graphics2D g2, String text, int x, int y, int maxWidth) {
        FontMetrics fm = g2.getFontMetrics();
        g2.setColor(Color.BLACK);

        List<String> lines = wrapText(text, fm, maxWidth);
        int lineHeight = fm.getHeight();
        int totalHeight = lines.size() * lineHeight;
        int startY = y - totalHeight / 2 + fm.getAscent();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            int w = fm.stringWidth(line);
            g2.drawString(line, x - w / 2, startY + i * lineHeight);
        }
    }

    // EFFECTS: returns a list of strings such that, when drawn, it fits inside a
    // node
    private List<String> wrapText(String text, FontMetrics fm, int maxWidth) {
        List<String> lines = new ArrayList<>();
        String[] words = text.split(" ");
        String currentLine = "";

        for (int i = 0; i < words.length; i++) {
            String word = words[i];

            if (currentLine.equals("")) {
                currentLine = word;
            } else {
                String test = currentLine + " " + word;
                if (fm.stringWidth(test) <= maxWidth) {
                    currentLine = test;
                } else {
                    lines.add(currentLine);
                    currentLine = word;
                }
            }
        }

        if (!currentLine.equals("")) {
            lines.add(currentLine);
        }

        return lines;
    }

    // MODIFIES: this
    // EFFECTS: updates the current node to the most recent selected node.
    // also resets the information on child nodes/circles.
    private void updateNode() {
        this.node = mindMap.getSelected();
        this.children = node.getChildren();
        childCircles.clear();
    }

    public void setMindMap(MindMap mindMap) {
        this.mindMap = mindMap;
    }

    public MindMap getMindMap() {
        return mindMap;
    }

    public List<Note> getChildren() {
        return children;
    }

    public Ellipse2D getMainCircle() {
        return mainCircle;
    }

    public List<Ellipse2D> getChildCircles() {
        return childCircles;
    }
}
