package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            OrgEntity org = new OrgEntity("sus");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // Exception expected to be caught!
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            OrgEntity org = new OrgEntity("csss");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkroom.json");
            writer.open();
            writer.write(org);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWorkroom.json");
            org = reader.read();
            assertEquals("csss", org.getOrgName());
            assertEquals(0, org.getSubEntityList().size());
        } catch (IOException e) {
            fail("Exception not expected.");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            OrgEntity org = new OrgEntity("csss");
            SubEntity comm = new SubEntity("Social", 6);
            org.addSubEntity(comm);
            org.addSubEntity(new SubEntity("Communication", 4));
            comm.addTask(new Task("Talk"));

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWorkroom.json");
            writer.open();
            writer.write(org);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWorkroom.json");
            org = reader.read();
            assertEquals("csss", org.getOrgName());
            List<SubEntity> subOrgs = org.getSubEntityList();
            assertEquals(2, subOrgs.size());
            checkSubOrg("Social", 6, subOrgs.get(0));
            checkSubOrg("Communication", 4, subOrgs.get(1));
            checkTask("Talk", comm.getTaskList().get(0));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}