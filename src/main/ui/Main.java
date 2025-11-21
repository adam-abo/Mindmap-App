package ui;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.MindMap;

@ExcludeFromJacocoGeneratedReport
public class Main {
    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("Mind Map");
        MindMap mindMap = new MindMap();

        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 700);

        MindMapUI network = new MindMapUI(mindMap);
        JPanel buttonPanel = new ButtonPanel(network);

        frame.add(network, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.NORTH);
        frame.setVisible(true);
    }
}