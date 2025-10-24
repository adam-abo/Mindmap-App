package persistence;

import model.MindMap;
import model.Node;
import model.Note;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

// Citation: JsonSerializationDemo's JsonTest was the base class for this class
@ExcludeFromJacocoGeneratedReport
public class JsonTest {
    protected void checkNote(String name, int childrenNum, Note note) {
        assertEquals(name, note.getContent());
        assertEquals(childrenNum, note.getChildren().size());
    }

    protected void readTestMindMap(MindMap mm) {
        List<Note> notes = mm.getChildren();
        List<Node> path = new ArrayList<>();
        path.add(mm);

        idCheckAll(mm);
        pathCheckAll(mm, path);
        assertNull(mm.getMovingNote());
        assertEquals(mm, mm.getSelected());

        assertEquals("mindful", mm.getContent());
        assertEquals(3, notes.size());
        checkNote("a", 2, mm.getChild(0));
        checkNote("b", 0, mm.getChild(1));
        checkNote("c", 0, mm.getChild(2));

        checkNote("a1", 0, mm.getChild(0).getChild(0));
        checkNote("a2", 1, mm.getChild(0).getChild(1));

        checkNote("a2Child", 0, mm.getChild(0).getChild(1).getChild(0));
    }

    // EFFECTS: checks that all Notes branching from a Node have correctly formatted
    // Ids
    private void idCheckAll(Node node) {
        for (int i = 0; i < node.getChildren().size(); i++) {
            if (i == node.getChildren().get(i).getId()) {
                idCheckAll(node.getChildren().get(i));
            } else {
                fail();
            }
        }
    }

    // EFFECTS: checks that all Nodes branching from a Node have correctly formatted
    // paths
    private void pathCheckAll(Node node, List<Node> path) {
        assertEquals(path, node.getPath());

        for (Note child : node.getChildren()) {
            List<Node> childPath = new ArrayList<>(path);
            childPath.add(child);
            pathCheckAll(child, childPath);
        }
    }
}
