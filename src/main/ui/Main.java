package ui;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ui.actions.EscapeAction;
import ui.actions.LoadAction;
import ui.actions.SaveAction;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.MindMap;

@ExcludeFromJacocoGeneratedReport
public class Main {
    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("Mind Map");
        MindMap mindMap = new MindMap();
        MindMapConsole console = new MindMapConsole(mindMap);
        MindMapUI network = new MindMapUI(mindMap);

        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 700);

        // Button panel at the top
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        JButton loadButton = new JButton("Load");
        JButton backButton = new JButton("Go Back");

        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(backButton);
        frame.add(buttonPanel, BorderLayout.NORTH);
        frame.add(network, BorderLayout.CENTER);
        frame.setVisible(true);

        saveButton.addActionListener(new SaveAction(mindMap));
        loadButton.addActionListener(new LoadAction(mindMap, network));
        backButton.addActionListener(new EscapeAction(mindMap, network));
    }
}