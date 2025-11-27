package ui.actions;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import model.Event;
import model.EventLog;

public class PrintLogAction extends WindowAdapter {

    @Override
    public void windowClosed(WindowEvent e) {
        for (Event event : EventLog.getInstance()) {
            System.out.println(event.getDescription() + "[" + event.getDate() + "]");
        }
    }

}
