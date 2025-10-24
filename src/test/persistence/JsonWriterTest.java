package persistence;

import model.Note;
import model.MindMap;
import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// Citation: this class is based on JsonSerializationDemo's JsonWriterTest
@ExcludeFromJacocoGeneratedReport
class JsonWriterTest extends JsonTest {
    // NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter
    // is to
    // write data to a file and then use the reader to read it back in and check
    // that we
    // read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            MindMap mm = new MindMap();
            mm.setContent("empty map");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyMindMap.json");
            writer.open();
            writer.write(mm);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyMindMap.json");
            mm = reader.read();
            assertEquals("empty map", mm.getContent());
            assertEquals(0, mm.getChildren().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            MindMap mm = new MindMap();
            mm.setContent("mindful");
            mm.addChild(new Note("a"));
            mm.addChild(new Note("b"));
            mm.addChild(new Note("c"));
            mm.setSelected(mm.getChild(0));
            mm.getSelected().addChild(new Note("a1"));
            mm.getSelected().addChild(new Note("a2"));
            mm.setSelected(mm.getSelected().getChild(1));
            mm.getSelected().addChild(new Note("a2Child"));

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralMindMap.json");
            writer.open();
            writer.write(mm);
            writer.close();

            JsonReader reader = new JsonReader("./data/testReaderGeneralMindMap.json");
            readTestMindMap(reader.read());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}