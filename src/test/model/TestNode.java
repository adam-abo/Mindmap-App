package model;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestNode {
    Node nodeA;

    @BeforeEach
    void runBefore() {
        nodeA = new Node("A", new ArrayList<Parent>());
        nodeA.path.add(nodeA);
        nodeA.setId(0);
    }

    @Test
    void testConstructor() {
        assertEquals(0, nodeA.getChildren().size());
        assertEquals("A", nodeA.getContent());

        idCheckAll(nodeA);
        pathCheckAll(nodeA, nodeA.path);
    }

    @Test
    void testConstructChild() {
        nodeA.constructChild("B");
        assertEquals(1, nodeA.getChildren().size());
        assertEquals("B", nodeA.getChildren().get(0).getContent());

        idCheckAll(nodeA);
        pathCheckAll(nodeA, nodeA.path);
    }

    @Test
    void testConstructChildMultiple() {
        nodeA.constructChild("B");
        nodeA.constructChild("C");
        assertEquals(2, nodeA.getChildren().size());
        assertEquals("B", nodeA.getChildren().get(0).getContent());
        assertEquals("C", nodeA.getChildren().get(1).getContent());

        idCheckAll(nodeA);
        pathCheckAll(nodeA, nodeA.path);
    }

    @Test
    void testConstructChildChain() {
        nodeA.constructChild("B");
        Node nodeB = nodeA.getChildren().get(0);
        nodeB.constructChild("C");

        assertEquals(1, nodeA.getChildren().size());
        assertEquals(1, nodeB.getChildren().size());
        assertEquals("B", nodeA.getChildren().get(0).getContent());
        assertEquals("C", nodeB.getChildren().get(0).getContent());

        idCheckAll(nodeA);
        pathCheckAll(nodeA, nodeA.path);
    }

    @Test
    void testConstructChildChainMultiple() {
        nodeA.constructChild("B");
        Node nodeB = nodeA.getChildren().get(0);
        nodeB.constructChild("C");
        nodeB.constructChild("D");

        assertEquals(1, nodeA.getChildren().size());
        assertEquals(2, nodeB.getChildren().size());
        assertEquals("B", nodeA.getChildren().get(0).getContent());
        assertEquals("C", nodeB.getChildren().get(0).getContent());

        idCheckAll(nodeA);
        pathCheckAll(nodeA, nodeA.path);
    }

    @Test
    void testDeleteChild() {
        nodeA.constructChild("B");
        nodeA.constructChild("C");
        nodeA.deleteChild(0);

        assertEquals(1, nodeA.getChildren().size());
        assertEquals("C", nodeA.getChildren().get(0).getContent());

        idCheckAll(nodeA);
        pathCheckAll(nodeA, nodeA.path);
    }

    @Test
    void testDeleteChildMultiple() {
        nodeA.constructChild("B");
        nodeA.constructChild("C");
        nodeA.deleteChild(1);

        assertEquals(1, nodeA.getChildren().size());
        assertEquals("B", nodeA.getChildren().get(0).getContent());

        nodeA.deleteChild(0);
        assertEquals(0, nodeA.getChildren().size());

        idCheckAll(nodeA);
        pathCheckAll(nodeA, nodeA.path);
    }

    @Test
    void testDeleteChildChain() {
        nodeA.constructChild("B");
        Node nodeB = nodeA.getChildren().get(0);
        nodeB.constructChild("C");
        nodeB.deleteChild(0);

        assertEquals(1, nodeA.getChildren().size());
        assertEquals("B", nodeA.getChildren().get(0).getContent());
        assertEquals(0, nodeB.getChildren().size());

        idCheckAll(nodeA);
        pathCheckAll(nodeA, nodeA.path);
    }

    @Test
    void testSetNote() {
        nodeA.setContent("Hooray!");
        assertEquals("Hooray!", nodeA.getContent());
    }

    // EFFECTS: checks that all nodes branching from a Parent have correct Ids
    private void idCheckAll(Node node) {
        for (int i = 0; i < node.getChildren().size(); i++) {
            if (i == node.getChildren().get(i).getId()) {
                idCheckAll(node.getChildren().get(i));
            } else {
                fail();
            }
        }
    }

    // EFFECTS: checks that all nodes branching from a Parent have correct paths
    private void pathCheckAll(Node node, List<Parent> path) {
        assertTrue(path.equals(node.getPath()));

        for (Node child : node.getChildren()) {
            List<Parent> childPath = new ArrayList<>(path);
            childPath.add(child);
            pathCheckAll(child, childPath);
        }
    }
}
