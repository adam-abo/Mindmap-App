package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestMindMap {
    MindMap mm;
    Node selected;

    @BeforeEach
    void runBefore() {
        mm = new MindMap();
        selected = mm.getSelected();
    }

    @Test
    void testConstructor() {
        assertEquals(mm, mm.getSelected());
        assertEquals(0, mm.getChildren().size());
        assertNull(mm.getContent());

        assertEquals(1, mm.getPath().size());
        assertEquals(mm, mm.getPath().get(0));
    }

    @Test
    void testConstructChildOfSelectedRoot() {
        mm.constructChildOfSelected("B");
        assertEquals(1, mm.getChildren().size());
        assertEquals(1, mm.getSelected().getChildren().size());
        assertEquals("B", mm.getChildren().get(0).getContent());
        assertEquals("B", mm.getSelected().getChildren().get(0).getContent());

        idCheckAll(mm);
        pathCheckAll(mm, mm.getPath());
    }

    @Test
    void testConstructChildOfSelected() {
        mm.constructChild("sel");
        mm.setSelected(mm.getChild(0));

        mm.constructChildOfSelected("B");
        assertEquals(1, mm.getChildren().size());
        assertEquals(1, mm.getSelected().getChildren().size());
        assertEquals("sel", mm.getChildren().get(0).getContent());
        assertEquals("B", mm.getSelected().getChildren().get(0).getContent());

        idCheckAll(mm);
        pathCheckAll(mm, mm.getPath());
    }

    @Test
    void testDelChildOfSelectedRoot() {
        mm.constructChild("B");
        mm.constructChild("C");
        mm.delChildOfSelected(1);

        assertEquals(1, mm.getChildren().size());
        assertEquals(1, mm.getSelected().getChildren().size());
        assertEquals("B", mm.getChildren().get(0).getContent());
        assertEquals("B", mm.getSelected().getChildren().get(0).getContent());

        idCheckAll(mm);
        pathCheckAll(mm, mm.getPath());
    }

    @Test
    void testDelChildOfSelected() {
        mm.constructChild("sel");
        mm.setSelected(mm.getChild(0));

        mm.constructChildOfSelected("B");
        mm.constructChildOfSelected("C");
        mm.delChildOfSelected(0);

        assertEquals(1, mm.getChildren().size());
        assertEquals(1, mm.getSelected().getChildren().size());
        assertEquals("sel", mm.getChildren().get(0).getContent());
        assertEquals("C", mm.getSelected().getChildren().get(0).getContent());

        idCheckAll(mm);
        pathCheckAll(mm, mm.getPath());
    }

    @Test
    void testSelectChildOfSelectedRoot() {
        mm.constructChildOfSelected("B");
        mm.constructChildOfSelected("C");
        mm.selectChildOfSelected(1);

        assertEquals("C", mm.getSelected().getContent());

        idCheckAll(mm);
        pathCheckAll(mm, mm.getPath());
    }

    @Test
    void testSelectChildOfSelected() {
        mm.constructChild("sel");
        mm.setSelected(mm.getChild(0));

        mm.constructChildOfSelected("B");
        mm.constructChildOfSelected("C");
        mm.selectChildOfSelected(1);

        assertEquals("C", mm.getSelected().getContent());

        idCheckAll(mm);
        pathCheckAll(mm, mm.getPath());
    }

    @Test
    void testSelectParentOfSelectedChild() {
        mm.constructChild("sel");
        mm.constructChild("distracting sibling");
        mm.setSelected(mm.getChild(0));

        mm.constructChildOfSelected("B");
        mm.selectParentOfSelected();

        assertEquals(mm, mm.getSelected());

        idCheckAll(mm);
        pathCheckAll(mm, mm.getPath());
    }

    @Test
    void testSelectParentOfSelectedGrandchild() {
        mm.constructChild("goal");
        mm.constructChild("distracting sibling");
        mm.setSelected(mm.getChild(0));

        mm.getSelected().constructChild("sel");
        mm.constructChild("another distracting sibling");
        mm.selectChildOfSelected(0);

        mm.constructChildOfSelected("B");
        mm.selectParentOfSelected();

        assertEquals("goal", mm.getSelected().getContent());

        idCheckAll(mm);
        pathCheckAll(mm, mm.getPath());
    }

    @Test
    void testConstructChild() {
        mm.constructChild("B");
        assertEquals(1, mm.getChildren().size());
        assertEquals("B", mm.getChildren().get(0).getContent());

        idCheckAll(mm);
        pathCheckAll(mm, mm.getPath());
    }

    @Test
    void testConstructChildMultiple() {
        mm.constructChild("B");
        mm.constructChild("C");
        assertEquals(2, mm.getChildren().size());
        assertEquals("B", mm.getChildren().get(0).getContent());
        assertEquals("C", mm.getChildren().get(1).getContent());

        idCheckAll(mm);
        pathCheckAll(mm, mm.getPath());
    }

    @Test
    void testConstructChildChain() {
        mm.constructChild("B");
        Note nodeB = mm.getChildren().get(0);
        nodeB.constructChild("C");

        assertEquals(1, mm.getChildren().size());
        assertEquals(1, nodeB.getChildren().size());
        assertEquals("B", mm.getChildren().get(0).getContent());
        assertEquals("C", nodeB.getChildren().get(0).getContent());

        idCheckAll(mm);
        pathCheckAll(mm, mm.getPath());
    }

    @Test
    void testConstructChildChainMultiple() {
        mm.constructChild("B");
        Note nodeB = mm.getChildren().get(0);
        nodeB.constructChild("C");
        nodeB.constructChild("D");

        assertEquals(1, mm.getChildren().size());
        assertEquals(2, nodeB.getChildren().size());
        assertEquals("B", mm.getChildren().get(0).getContent());
        assertEquals("C", nodeB.getChildren().get(0).getContent());

        idCheckAll(mm);
        pathCheckAll(mm, mm.getPath());
    }

    @Test
    void testDeleteChild() {
        mm.constructChild("B");
        mm.constructChild("C");
        mm.deleteChild(0);

        assertEquals(1, mm.getChildren().size());
        assertEquals("C", mm.getChildren().get(0).getContent());

        idCheckAll(mm);
        pathCheckAll(mm, mm.getPath());
    }

    @Test
    void testDeleteChildMultiple() {
        mm.constructChild("B");
        mm.constructChild("C");
        mm.deleteChild(1);

        assertEquals(1, mm.getChildren().size());
        assertEquals("B", mm.getChildren().get(0).getContent());

        mm.deleteChild(0);
        assertEquals(0, mm.getChildren().size());

        idCheckAll(mm);
        pathCheckAll(mm, mm.getPath());
    }

    @Test
    void testDeleteChildChain() {
        mm.constructChild("B");
        Note nodeB = mm.getChildren().get(0);
        nodeB.constructChild("C");
        nodeB.deleteChild(0);

        assertEquals(1, mm.getChildren().size());
        assertEquals("B", mm.getChildren().get(0).getContent());
        assertEquals(0, nodeB.getChildren().size());

        idCheckAll(mm);
        pathCheckAll(mm, mm.getPath());
    }

    @Test
    void testSetContent() {
        mm.setContent("Hooray!");
        assertEquals("Hooray!", mm.getContent());
    }

    // The following methods are run at the end of each test as a general check that the path and id of the 
    // entire mindmap structure is sound

    // EFFECTS: checks that all Notes branching from a Node have correct Ids
    private void idCheckAll(Node node) {
        for (int i = 0; i < node.getChildren().size(); i++) {
            if (i == node.getChildren().get(i).getId()) {
                idCheckAll(node.getChildren().get(i));
            } else {
                fail();
            }
        }
    }

    // EFFECTS: checks that all Nodes branching from a Node have correct paths
    private void pathCheckAll(Node node, List<Node> path) {
        assertEquals(path, node.getPath());

        for (Note child : node.getChildren()) {
            List<Node> childPath = new ArrayList<>(path);
            childPath.add(child);
            pathCheckAll(child, childPath);
        }
    }
}
