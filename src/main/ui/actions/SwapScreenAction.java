package ui.actions;

import java.awt.event.MouseAdapter;
import javax.swing.JFrame;
import javax.swing.JWindow;

import java.awt.event.MouseEvent;

// This class represents the actions that occur due to mouse clicks on the intro frame
public class SwapScreenAction extends MouseAdapter {
    JWindow closeWindow;
    JFrame openFrame;

    public SwapScreenAction(JWindow closeWindow, JFrame openFrame) {
        this.closeWindow = closeWindow;
        this.openFrame = openFrame;
    }

    // EFFECTS: swaps screens on click
    @Override
    public void mouseClicked(MouseEvent e) {
        swap();
    }

    // MODIFIES: this
    // EFFECTS: closes the intro window and reveals the new frame
    private void swap() {
        closeWindow.setVisible(false);
        closeWindow.dispose();
        openFrame.setVisible(true);
    }
}
