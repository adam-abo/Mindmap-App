package ui;

import javax.swing.JButton;
import javax.swing.JPanel;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import ui.actions.BackAction;
import ui.actions.LoadAction;
import ui.actions.SaveAction;

// This panel just contains the three buttons at the top of the screen
@ExcludeFromJacocoGeneratedReport
public class ButtonPanel extends JPanel {

    public ButtonPanel(MindMapUI network) {
        JButton saveButton = new JButton("Save");
        JButton loadButton = new JButton("Load");
        JButton backButton = new JButton("Go Back");

        add(saveButton);
        add(loadButton);
        add(backButton);

        saveButton.addActionListener(new SaveAction(network));
        loadButton.addActionListener(new LoadAction(network));
        backButton.addActionListener(new BackAction(network));
    }
}
