package ui;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ui.actions.BackAction;
import ui.actions.LoadAction;
import ui.actions.SaveAction;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.MindMap;

@ExcludeFromJacocoGeneratedReport
public class Main {
    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("Mind Map");
        MindMap mindMap = new MindMap();
        //MindMapConsole console = new MindMapConsole(mindMap);

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