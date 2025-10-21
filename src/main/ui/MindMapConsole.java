package ui;

import model.*;

import java.util.InputMismatchException;
import java.util.Scanner;

/*  
* This class is responsible for both user control over the mindmap, as well as being the root node for all child nodes.
* It extends Parent's behaviour of having children.
*/
public class MindMapConsole {
    private Scanner scanner;
    private boolean isRunning;
    private MindMap mindMap;

    public MindMapConsole() {
        isRunning = true;
        mindMap = new MindMap();
        scanner = new Scanner(System.in);
        intro();

        while (isRunning) {
            action();
        }
    }

    // MODIFIES: this, Parent
    // EFFECTS: this method is responsible for the actions of the program
    // A title/note is displayed, then the children of the selected node
    // then the user controls are displayed, then they are processed after input.
    public void action() {
        if (mindMap.getSelected().equals(mindMap)) {
            print("Mindmap Title: " + mindMap.getContent());
        } else {
            print("Note: " + mindMap.getSelected().getContent());
        }

        displayChildren();
        displayControls();
        processInput(scanner.nextLine());
    }

    // MODIFIES: this
    // EFFECTS: first message asks the user to title their mindmap
    public void intro() {
        print("Please begin by titling your mindmap:");
        mindMap.setContent(scanner.nextLine());
    }

    // EFFECTS: displays the sub-notes of the current selected node
    public void displayChildren() {
        print("\nSub-notes: ");
        for (Note node : mindMap.getSelected().getChildren()) {
            print("- " + node.getContent());
        }
    }

    // EFFECTS: displays the controls for each process option
    public void displayControls() {
        divider();
        print("Select an option:\n");
        print("a: Add a new connected sub-note");
        print("b: Go back to the previous node");
        print("s: Select a sub-note to expand its contents");
        print("d: Select and delete a sub-note and its branch");
        print("q: Quit the program\n");
    }

    // MODIFIES: this, Parent
    // EFFECTS: processes the user's input
    public void processInput(String input) {
        switch (input) {
            case "a":
                addNote();
                break;
            case "b":
                back();
                break;
            case "s":
                selectNote();
                break;
            case "d":
                deleteNote();
                break;
            case "q":
                quit();
                break;
            default:
                System.out.println("Invalid input, try again.");
        }
        divider();
    }

    // MODIFIES: mindMap
    // EFFECTS: adds a child with inputted user content to the selected node
    public void addNote() {
        print("Please enter the content of the new note: ");
        String content = scanner.nextLine();
        mindMap.constructChildOfSelected(content);
    }

    // MODIFIES: mindMap
    // EFFECTS: deletes a child of the currently selected node
    // If list of children is empty, warn user
    // If index inputted is out of bounds, warn user
    public void deleteNote() {
        try {
            mindMap.delChildOfSelected(selectIndex());
        } catch (IndexOutOfBoundsException e) {
            print("Index is out of bounds");
        } catch (NoChildrenException e) {
            print("This node has no sub-notes to select");
        }
    }

    // MODIFIES: mindMap
    // EFFECTS: selects a sub-node via user inputted index
    // If index inputted is out of bounds, warn user
    // If list of children is empty, warn user
    public void selectNote() {
        try {
            mindMap.selectChildOfSelected(selectIndex());
        } catch (IndexOutOfBoundsException e) {
            print("Index is out of bounds");
        } catch (NoChildrenException e) {
            print("This node has no sub-notes to select");
        }
    }

    // REQUIRES: input must not include whitespace
    // EFFECTS: returns a valid user-inputted index
    // If list of children is empty, throws NoChildrenException instead
    public int selectIndex() throws NoChildrenException {
        if (mindMap.getSelected().getChildren().size() > 0) {
            print("Select the note by its index");

            while (true) {
                try {
                    int input = scanner.nextInt();
                    scanner.nextLine();

                    if (input >= mindMap.getSelected().getChildren().size()) {
                        throw new IndexOutOfBoundsException();
                    }
                    return input;
                } catch (InputMismatchException e) {
                    print("Not an integer, try again\n");
                    scanner.nextLine();
                }
            }
        } else {
            throw new NoChildrenException();
        }
    }

    // MODIFIES: mindMap
    // EFFECTS: selects the parent of the current selected node if possible,
    // otherwise prints a warning
    private void back() {
        try {
            mindMap.selectParentOfSelected();
        } catch (IndexOutOfBoundsException e) {
            print("Cannot go back further, this is the start of the mindmap!");
        }
    }

    // MODIFIES: this
    // EFFECTS: prevents all future actions in order to stop the program
    public void quit() {
        print("Goodbye!");
        this.isRunning = false;
    }

    // EFFECTS: simplifies the println call
    private void print(String txt) {
        System.out.println(txt);
    }

    // EFFECTS: prints a line of dashes for improved readability
    private void divider() {
        print("----------------------------------");
    }

    public boolean getIsRunning() {
        return isRunning;
    }
}