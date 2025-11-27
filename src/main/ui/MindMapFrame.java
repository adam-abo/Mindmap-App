package ui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import ui.actions.PrintLogAction;

// This is the main frame where the MindMapUI and buttons exist
@ExcludeFromJacocoGeneratedReport
public class MindMapFrame extends JFrame {
    MindMapFrame(String title) {
        super(title);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 700);
        getContentPane().setBackground(Color.LIGHT_GRAY);

        MindMapUI network = new MindMapUI();
        JPanel buttonPanel = new ButtonPanel(network);
        network.setOpaque(false);
        buttonPanel.setOpaque(false);

        add(network, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.NORTH);

        addWindowListener(new PrintLogAction());
    }
}
