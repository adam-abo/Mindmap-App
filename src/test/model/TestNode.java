package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestNode {
    Node node1, node2, node3;

    @BeforeEach
    void runBefore() {
        node1 = new Node("Hello");
        node2 = new Node("Wow");
        node3 = new Node("Bye");
    }

    @Test
    void testConstructor() {
        assertEquals(0, node1.getChildren().size());
        assertEquals("Hello", node1.getNote());
    }

    @Test
    void testAddChild() {
        node1.addChild(node2);
        assertEquals(1, node1.getChildren().size());
        assertEquals("Wow", node1.getChildren().get(0).getNote());
    }

    @Test
    void testAddChildMultiple() {
        node1.addChild(node2);
        node1.addChild(node3);
        assertEquals(2, node1.getChildren().size());
        assertEquals("Wow", node1.getChildren().get(0).getNote());
        assertEquals("Bye", node1.getChildren().get(1).getNote());
    }

    @Test
    void testAddChildChain() {
        node1.addChild(node2);
        node2.addChild(node3);
        assertEquals(1, node1.getChildren().size());
        assertEquals("Wow", node1.getChildren().get(0).getNote());
        assertEquals("Bye", node2.getChildren().get(0).getNote());
    }

    @Test
    void testConstructChild() {
        node1.constructChild("No Way!");
        assertEquals(1, node1.getChildren().size());
        assertEquals("No Way!", node1.getChildren().get(0).getNote());
    }

    @Test
    void testConstructChildMultiple() {
        node1.constructChild("No Way!");
        node1.constructChild("Yes Way!");
        assertEquals(2, node1.getChildren().size());
        assertEquals("No Way!", node1.getChildren().get(0).getNote());
        assertEquals("Yes Way!", node2.getChildren().get(1).getNote());
    }

    @Test
    void testConstructChildChain() {
        node1.constructChild("No Way!");
        Node childNode = node1.getChildren().get(0);
        childNode.constructChild("Yes Way!");

        assertEquals(1, node1.getChildren().size());
        assertEquals("No Way!", childNode.getNote());
        assertEquals(1, childNode.getChildren().size());
        assertEquals("Yes Way!", childNode.getChildren().get(0).getNote());
    }

    @Test
    void testDeleteChild() {
        node1.addChild(node2);
        node1.addChild(node3);
        node1.deleteChild(0);
        
        assertEquals(1, node1.getChildren().size());
        assertEquals("Bye", node1.getChildren().get(0).getNote());
    }

    @Test
    void testDeleteChildMultiple() {
        node1.addChild(node2);
        node1.addChild(node3);
        node1.deleteChild(1);
        
        assertEquals(1, node1.getChildren().size());
        assertEquals("Wow", node1.getChildren().get(0).getNote());

        node1.deleteChild(0);
        assertEquals(0, node1.getChildren().size());
    }

    @Test
    void testSetNote() {
        node1.setnote("Hooray!");
        assertEquals("Hooray", node1.getNote());
    }
}
