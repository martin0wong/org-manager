package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            OrgEntity org = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // Exception expected to be caught!
        }
    }

    @Test
    void testReaderEmptyOrg() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyOrg.json");
        try {
            OrgEntity org = reader.read();
            assertEquals("csss", org.getOrgName());
            assertEquals(0, org.getSubEntityList().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralOrg() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralOrg.json");
        try {
            OrgEntity org = reader.read();
            assertEquals("csss", org.getOrgName());
            List<SubEntity> subOrgs = org.getSubEntityList();
            assertEquals(2, org.getSubEntityList().size());
            checkSubOrg("Internal", 10, subOrgs.get(0));
            checkSubOrg("External", 5, subOrgs.get(1));

            assertEquals(1,subOrgs.get(0).getTaskList().size());
            checkTask("a", subOrgs.get(0).getTaskList().get(0));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}