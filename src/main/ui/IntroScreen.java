package ui;

import javax.swing.*;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import ui.actions.SwapScreenAction;

import java.awt.*;

// Constructs and organizes all the content on the intro screen
// It is also responsible for hiding itself and showing the mindmap screen when the user clicks
@ExcludeFromJacocoGeneratedReport
public class IntroScreen extends JWindow {

    public IntroScreen() {
        JPanel panel = createMainPanel();

        panel.add(Box.createVerticalGlue());
        panel.add(createLabel("Welcome to your", 40, Font.BOLD));
        panel.add(createTitlePanel());
        panel.add(Box.createVerticalStrut(30));
        addInstructionLines(panel);
        panel.add(Box.createVerticalStrut(25));
        panel.add(createLabel("Click anywhere to begin!", 22, Font.PLAIN));
        panel.add(Box.createVerticalGlue());

        add(panel);
        setSize(700, 700);
        showUntilClick();
    }

    // EFFECTS: creates the panel on which all the components will be laid on
    private JPanel createMainPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        return panel;
    }

    // EFFECTS: creates a panel for the title message (includes the image)
    private JPanel createTitlePanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        ImageIcon originalIcon = new ImageIcon("./data/image.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(65, 45, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        imageLabel.setAlignmentY(Component.CENTER_ALIGNMENT);

        JLabel title2 = new JLabel("indmap!");
        title2.setFont(new Font("Arial", Font.BOLD, 40));
        title2.setAlignmentY(Component.CENTER_ALIGNMENT);

        panel.add(imageLabel);
        panel.add(title2);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return panel;
    }

    // EFFECTS: creates a text label with given qualities
    private JLabel createLabel(String text, int size, int style) {
        JLabel label = new JLabel(text);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("Arial", style, size));
        return label;
    }

    // EFFECTS: adds all the intrusctions for use to the main panel
    private void addInstructionLines(JPanel panel) {
        panel.add(
                createLabel("Ctrl-click on: center node to add new notes / child node to expand it.", 18, Font.PLAIN));
        panel.add(Box.createVerticalStrut(8));
        panel.add(createLabel("Shift-click on: child node to cut it / center node to paste the last cut node once.", 18,
                Font.PLAIN));
        panel.add(Box.createVerticalStrut(8));
        panel.add(createLabel("Click any node to edit its contents.", 18, Font.PLAIN));
    }

    // MODIFIES: this
    // EFFECTS: adds a mouseListener to detect a user click to swap screens
    public void showUntilClick() {
        setVisible(true);
        addMouseListener(new SwapScreenAction(this, new MindMapFrame("Mind Map")));
    }
}
