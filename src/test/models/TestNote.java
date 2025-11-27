package models;

import static org.junit.jupiter.api.Assertions.*;
import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.Node;
import model.Note;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@ExcludeFromJacocoGeneratedReport
public class TestNote {
    Note noteA;
    List<Node> pathA;

    @BeforeEach
    void runBefore() {
        noteA = new Note("A");
        pathA = new ArrayList<>();

        noteA.setId(0);
        pathA.add(noteA);
        noteA.setPath(pathA);
    }

    @Test
    void testConstructor() {
        Note noteB = new Note("B");
        assertEquals(0, noteB.getChildren().size());
        assertEquals("B", noteB.getContent());

        assertNull(noteB.getPath());
        assertEquals(0, noteB.getId());
    }

    @Test
    void testAddChildMultiple() {
        Note noteB = new Note("B");
        Note noteC = new Note("C");
        noteA.addChild(noteB);
        noteA.addChild(noteC);

        assertEquals(2, noteA.getChildren().size());
        assertEquals("B", noteA.getChildren().get(0).getContent());
        assertEquals("C", noteA.getChildren().get(1).getContent());

        idCheckAll(noteA);
        pathCheckAll(noteA, noteA.getPath());
    }

    @Test
    void testConstructChild() {
        noteA.constructChild("B");
        assertEquals(1, noteA.getChildren().size());
        assertEquals("B", noteA.getChildren().get(0).getContent());

        idCheckAll(noteA);
        pathCheckAll(noteA, noteA.getPath());
    }

    @Test
    void testConstructChildMultiple() {
        noteA.constructChild("B");
        noteA.constructChild("C");
        assertEquals(2, noteA.getChildren().size());
        assertEquals("B", noteA.getChildren().get(0).getContent());
        assertEquals("C", noteA.getChildren().get(1).getContent());

        idCheckAll(noteA);
        pathCheckAll(noteA, noteA.getPath());
    }

    @Test
    void testConstructChildChain() {
        noteA.constructChild("B");
        Note nodeB = noteA.getChildren().get(0);
        nodeB.constructChild("C");

        assertEquals(1, noteA.getChildren().size());
        assertEquals(1, nodeB.getChildren().size());
        assertEquals("B", noteA.getChildren().get(0).getContent());
        assertEquals("C", nodeB.getChildren().get(0).getContent());

        idCheckAll(noteA);
        pathCheckAll(noteA, noteA.getPath());
    }

    @Test
    void testConstructChildChainMultiple() {
        noteA.constructChild("B");
        Note nodeB = noteA.getChildren().get(0);
        nodeB.constructChild("C");
        nodeB.constructChild("D");

        assertEquals(1, noteA.getChildren().size());
        assertEquals(2, nodeB.getChildren().size());
        assertEquals("B", noteA.getChildren().get(0).getContent());
        assertEquals("C", nodeB.getChildren().get(0).getContent());

        idCheckAll(noteA);
        pathCheckAll(noteA, noteA.getPath());
    }

    @Test
    void testDeleteChild() {
        noteA.constructChild("B");
        noteA.constructChild("C");
        noteA.deleteChild(0);

        assertEquals(1, noteA.getChildren().size());
        assertEquals("C", noteA.getChildren().get(0).getContent());

        idCheckAll(noteA);
        pathCheckAll(noteA, noteA.getPath());
    }

    @Test
    void testDeleteChildMultiple() {
        noteA.constructChild("B");
        noteA.constructChild("C");
        noteA.deleteChild(1);

        assertEquals(1, noteA.getChildren().size());
        assertEquals("B", noteA.getChildren().get(0).getContent());

        noteA.deleteChild(0);
        assertEquals(0, noteA.getChildren().size());

        idCheckAll(noteA);
        pathCheckAll(noteA, noteA.getPath());
    }

    @Test
    void testDeleteChildChain() {
        noteA.constructChild("B");
        Note nodeB = noteA.getChildren().get(0);
        nodeB.constructChild("C");
        nodeB.deleteChild(0);

        assertEquals(1, noteA.getChildren().size());
        assertEquals("B", noteA.getChildren().get(0).getContent());
        assertEquals(0, nodeB.getChildren().size());

        idCheckAll(noteA);
        pathCheckAll(noteA, noteA.getPath());
    }

    @Test
    void testSetContent() {
        noteA.setContent("Hooray!");
        assertEquals("Hooray!", noteA.getContent());
    }

    // These methods are run at the end of each test as a general check that the path and id of all the notes is sound

    // EFFECTS: checks that all nodes branching from a Parent have correct Ids
    private void idCheckAll(Note note) {
        for (int i = 0; i < note.getChildren().size(); i++) {
            if (i == note.getChildren().get(i).getId()) {
                idCheckAll(note.getChildren().get(i));
            } else {
                fail();
            }
        }
    }

    // EFFECTS: checks that all nodes branching from a Parent have correct paths
    private void pathCheckAll(Note note, List<Node> path) {
        assertEquals(path, note.getPath());

        for (Note child : note.getChildren()) {
            List<Node> childPath = new ArrayList<>(path);
            childPath.add(child);
            pathCheckAll(child, childPath);
        }
    }
}

