package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.swing.JFrame;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

/*  
* This class is responsible for both user control over the mindmap, as well as being the root node for all child nodes.
* It extends Parent's behaviour of having children.
*/

@ExcludeFromJacocoGeneratedReport
public class MindMapConsole {
    private static final String JSON_STORE = "./data/mindmap.json";
    private Scanner scanner;
    private boolean isRunning;
    private boolean isRootNode;
    private boolean isChildrenEmpty;
    private boolean isMoving;
    private MindMap mindMap;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    public MindMapConsole(JFrame frame) {
        isRunning = true;
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        scanner = new Scanner(System.in);
        mindMap = new MindMap();
        intro();
        
        NodeNetwork network = new NodeNetwork(mindMap);
        frame.add(network);
        frame.setVisible(true);

        while (isRunning) {
            action();
            network.repaint();
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
        print("l: Load from file a previous the last saved state of the program");
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
        } else if (input.equals("l")) {
            loadMindMap();
        } else if (input.equals("q")) {
            quit();
        } else {
            print("Invalid input.");
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
    // EFFECTS: asks the user to whether they want to save the mindmap,
    // then prevents all future actions in order to stop the program
    public void quit() {
        print("Save the current mindmap? (y/n)");
        String input = scanner.nextLine().toLowerCase();

        switch (input) {
            case "y":
                saveMindMap();
            case "n":
                print("Goodbye!");
                this.isRunning = false;
                break;
            default:
                print("Invalid input, try again.");
                quit();
        }

    }

    // EFFECTS: saves the mindmap to file
    private void saveMindMap() {
        try {
            jsonWriter.open();
            jsonWriter.write(mindMap);
            jsonWriter.close();
            print("Saved " + mindMap.getContent() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            print("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads mindmap from file.
    // mindMap.getSelected() and mindMap.getgetMovingNote() will be reset, as it
    // would be somewhat
    // strange for the user to have their values persist
    public void loadMindMap() {
        try {
            mindMap = jsonReader.read();
            print("Loaded " + mindMap.getContent() + " from " + JSON_STORE);
        } catch (IOException e) {
            print("Unable to read from file: " + JSON_STORE);
        } catch (org.json.JSONException e) {
            print("No saved data to load");
        }
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