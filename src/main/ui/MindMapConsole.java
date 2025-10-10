package ui;

import model.*;

import java.util.InputMismatchException;
import java.util.Scanner;


/*  
* This class is responsible for both user control over the mindmap, as well as being the root node for all child nodes.
* It extends Parent's behaviour of having children.
*/
public class MindMapConsole extends Parent {
    protected Scanner scanner;
    private boolean isRunning;
    private Parent selected;

    public MindMapConsole() {
        isRunning = true;
        selected = this;
        scanner = new Scanner(System.in);
        intro();

        while (isRunning) {
            action();
        }
    }

    // MODIFIES: this, Parent
    // EFFECTS: this method is responsible for the actions of the program
    // A title/note is displayed, then the children of the selected node
    // then the user controls are display, then they are processed after input.
    public void action() {
        if (selected.getPath().size() <= 1) {
            print("Mindmap Title: " + selected.getContent());
        } else {
            print("Note: " + selected.getContent());
        }

        displayChildren();
        displayControls();
        processInput(scanner.nextLine());
    }

    // MODIFIES: this
    // EFFECTS: first message asks the user to title their mindmap
    public void intro() {
        print("Please begin by titling your mindmap:");
        selected.setContent(scanner.nextLine());
    }

    // EFFECTS: displays both the content of selected node and its children
    public void displayChildren() {
        print("\nSub-notes: ");
        for (Node node : selected.getChildren()) {
            print("- " + node.getContent());
        }
    }

    // EFFECTS: displays the controls for each process option
    public void displayControls() {
        divider();
        print("Select an option:\n");
        print("a: Add a new connected sub-note");
        print("b: Go back to the previous node");
        print("s: Select a sub-note to expand and add sub-notes to it");
        print("d: Select and delete a sub-note and its branch");
        print("q: Quit the program\n");
    }

    // MODIFIES: this, Parent
    // EFFECTS: processes the user's input
    public void processInput(String input) {
        switch (input) {
            case "a":
                addChild();
                break;
            case "b":
                back();
                break;
            case "s":
                selectChild();
                break;
            case "d":
                delChild();
                break;
            case "q":
                quit();
                break;
            default:
                System.out.println("Invalid input, try again.");
        }
        divider();
    }

    // MODIFIES: this, Parent
    // EFFECTS: adds a child with inputted user content to the selected node
    public void addChild() {
        print("Please enter the content of the new note: ");
        String content = scanner.nextLine();
        selected.constructChild(content);
    }

    // MODIFIES: this, Parent
    // EFFECTS: deletes a child of the currently selected node
    // If list of children is empty, warn user
    // If index inputted is out of bounds, warn user
    public void delChild() {
        try {
            selected.deleteChild(selectIndex());
        } catch (IndexOutOfBoundsException e) {
            print("Index is out of bounds");
        } catch (NoChildrenException e) {
            print("This node has no sub-nodes to select");
        }
    }

    // MODIFIES: this, Parent
    // EFFECTS: selects a sub-node via user inputted index
    // If list of children is empty, warn user
    // If index inputted is out of bounds, warn user
    public void selectChild() {
        try {
            selected = selected.getChildren().get(selectIndex());
        } catch (IndexOutOfBoundsException e) {
            print("Index is out of bounds");
            selectChild();
        } catch (NoChildrenException e) {
            print("This node has no sub-nodes to select");
        }
    }

    // REQUIRES: input must not include whitespace
    // EFFECTS: returns a valid user-inputted index
    // If list of children is empty, throws NoChildrenException instead
    // If index inputted is out of bounds, throws IndexOutOfBoundsException
    public int selectIndex() throws NoChildrenException, IndexOutOfBoundsException {
        if (selected.getChildren().size() > 0) {
            print("Select the note by its index");

            while (true) {
                try {
                    int input = scanner.nextInt();
                    scanner.nextLine();

                    if (input >= selected.getChildren().size()) {
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

    // MODIFIES: this
    // EFFECTS: selects the parent of the current selected node if possible
    private void back() {
        if (selected.getPath().size() > 1) {
            selected = selected.getPath().get(selected.getPath().size() - 2);
        } else {
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
    public  void print(String txt){
        System.out.println(txt);
    }

    // EFFECTS: prints a line of dashes for improved readability
    private void divider() {
        print("----------------------------------");
    }
}