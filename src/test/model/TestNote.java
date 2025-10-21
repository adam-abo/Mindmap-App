package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestNote {
    Note noteA;

    @BeforeEach
    void runBefore() {
        noteA = new Note("A", new ArrayList<Node>());
        noteA.setId(0);
    }

    @Test
    void testConstructor() {
        assertEquals(0, noteA.getChildren().size());
        assertEquals("A", noteA.getContent());

        assertEquals(1, noteA.getPath().size());
        assertEquals(noteA, noteA.getPath().get(0));
    }

    @Test
    void testConstructChild() {
        noteA.constructChild("B");
        assertEquals(1, noteA.getChildren().size());
        assertEquals("B", noteA.getChildren().get(0).getContent());

        idCheckAll(noteA);
        pathCheckAll(noteA, noteA.path);
    }

    @Test
    void testConstructChildMultiple() {
        noteA.constructChild("B");
        noteA.constructChild("C");
        assertEquals(2, noteA.getChildren().size());
        assertEquals("B", noteA.getChildren().get(0).getContent());
        assertEquals("C", noteA.getChildren().get(1).getContent());

        idCheckAll(noteA);
        pathCheckAll(noteA, noteA.path);
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
        pathCheckAll(noteA, noteA.path);
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
        pathCheckAll(noteA, noteA.path);
    }

    @Test
    void testDeleteChild() {
        noteA.constructChild("B");
        noteA.constructChild("C");
        noteA.deleteChild(0);

        assertEquals(1, noteA.getChildren().size());
        assertEquals("C", noteA.getChildren().get(0).getContent());

        idCheckAll(noteA);
        pathCheckAll(noteA, noteA.path);
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
        pathCheckAll(noteA, noteA.path);
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
        pathCheckAll(noteA, noteA.path);
    }

    @Test
    void testSetContent() {
        noteA.setContent("Hooray!");
        assertEquals("Hooray!", noteA.getContent());
    }

    // The following methods are run at the end of each test as a general check that the path and id of all the notes is sound

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

