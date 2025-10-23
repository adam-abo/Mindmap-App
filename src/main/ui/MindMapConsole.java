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
    private boolean isRootNode;
    private boolean isChildrenEmpty;
    private boolean isMoving;
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
        isRootNode = (mindMap == mindMap.getSelected());
        isChildrenEmpty = mindMap.getSelected().getChildren().isEmpty();
        isMoving = (mindMap.getMovingNote() != null);

        if (isRootNode) {
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
        if (!isMoving) {
            print("a: Add a new connected sub-note");
            print("e: Edit the note");
            if (!isChildrenEmpty) {
                print("d: Select and delete a sub-note and its branch");
                print("m: Select a sub-note to cut and paste it elsewhere");
            }
        } else {
            print("m: Paste the previously cut note under this current note");
        }
        if (!isChildrenEmpty) {
            print("s: Select a sub-note to expand its contents");
        }
        if (!isRootNode) {
            print("b: Go back to the previous node");
        }
        print("q: Quit the program\n");
    }

    // MODIFIES: this, Parent
    // EFFECTS: processes the user's input
    public void processInput(String input) {
        if (input.equals("a") && !isMoving) {
            addNote();
        } else if (input.equals("e") && !isMoving) {
            editNote();
        } else if (input.equals("d") && !isMoving && !isChildrenEmpty) {
            mindMap.delChildOfSelected(selectIndex());
        } else if (input.equals("m") && !isMoving && !isChildrenEmpty) {
            mindMap.setMovingNote(mindMap.delChildOfSelected(selectIndex()));
        } else if (input.equals("m") && isMoving) {
            mindMap.moveNote();
        } else if (input.equals("s") && !isChildrenEmpty) {
            mindMap.selectChildOfSelected(selectIndex());
        } else if (input.equals("b") && !isRootNode) {
            mindMap.selectParentOfSelected();
        } else if (input.equals("q")) {
            quit();
        } else {
            System.out.println("Invalid input, try again.");
        }
        divider();
    }

    // MODIFIES: mindMap
    // EFFECTS: adds a child with inputted user content to the selected node
    public void addNote() {
        print("Please enter the content of the new note: ");
        mindMap.constructChildOfSelected(scanner.nextLine());
    }

    // MODIFIES: mindMap
    // EFFECTS: edits the content of the current note
    public void editNote() {
        print("Please enter the new content of the note: ");
        mindMap.getSelected().setContent(scanner.nextLine());
    }

    // REQUIRES: input must not include whitespace
    // EFFECTS: returns a valid user-inputted index
    // If list of children is empty, throws NoChildrenException instead
    public int selectIndex() {
        print("Select the note by its index");

        while (true) {
            try {
                int input = scanner.nextInt();
                scanner.nextLine();
                mindMap.getSelected().getChildren().get(input);
                return input;
            } catch (InputMismatchException e) {
                print("Not an integer, try again\n");
                scanner.nextLine();
            } catch (IndexOutOfBoundsException e) {
                print("Index is out of bounds\n");
                scanner.nextLine();
            }
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