package ui;

import javax.swing.JFrame;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

@ExcludeFromJacocoGeneratedReport
public class Main {
    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("Node Network");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 700);

        new MindMapConsole(frame);
    }
}
