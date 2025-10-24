package persistence;

import model.MindMap;
import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// Citation: this class is based on JsonSerializationDemo's JsonReaderTest
@ExcludeFromJacocoGeneratedReport
class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyMindMap() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyMindMap.json");
        try {
            MindMap mm = reader.read();
            assertEquals("empty map", mm.getContent());
            assertEquals(0, mm.getChildren().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralMindMap() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralMindMap.json");
        try {
            readTestMindMap(reader.read());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}