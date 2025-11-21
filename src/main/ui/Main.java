package ui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.MindMap;

@ExcludeFromJacocoGeneratedReport
public class Main {
    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("Mind Map");

        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 700);
        frame.getContentPane().setBackground(Color.LIGHT_GRAY);

        MindMapUI network = new MindMapUI(new MindMap());
        JPanel buttonPanel = new ButtonPanel(network);
        network.setOpaque(false);
        buttonPanel.setOpaque(false);

        frame.add(network, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.NORTH);
        frame.setVisible(true);
    }
}