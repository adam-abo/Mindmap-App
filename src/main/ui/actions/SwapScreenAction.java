package ui.actions;

import java.awt.event.MouseAdapter;
import javax.swing.JFrame;
import java.awt.event.MouseEvent;

import ui.IntroScreen;

// This class represents the actions that occur due to mouse clicks on the intro frame
public class SwapScreenAction extends MouseAdapter {
    IntroScreen closeFrame;
    JFrame openFrame;

    public SwapScreenAction(IntroScreen closeFrame, JFrame openFrame) {
        this.closeFrame = closeFrame;
        this.openFrame = openFrame;
    }

    // EFFECTS: swaps screens on click
    @Override
    public void mouseClicked(MouseEvent e) {
        swap();
    }

    // MODIFIES: this
    // EFFECTS: closes this window and reveals the MindMap screen
    private void swap() {
        closeFrame.setVisible(false);
        closeFrame.dispose();
        openFrame.setVisible(true);
    }
}
