package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrgEntityTest {
    private OrgEntity testOrg;
    private SubEntity academic;
    private SubEntity internal;

    @BeforeEach
    void runBefore() {
        testOrg = new OrgEntity("Test Org");
        academic = new SubEntity("Academic", 5);
        internal = new SubEntity("Internal", 15);
    }

    @Test
    void addSubEntityTest() {
        assertEquals(0, testOrg.getSubEntityList().size());
        testOrg.addSubEntity(academic);
        assertEquals(1, testOrg.getSubEntityList().size());
        testOrg.addSubEntity(internal);
        assertEquals(2, testOrg.getSubEntityList().size());
    }

    @Test
    void removeSubEntityTest() {
        testOrg.addSubEntity(academic);
        testOrg.addSubEntity(internal);
        assertEquals(2, testOrg.getSubEntityList().size());

        testOrg.removeSubEntity(academic);
        assertEquals(1, testOrg.getSubEntityList().size());
        testOrg.removeSubEntity(internal);
        assertEquals(0, testOrg.getSubEntityList().size());
    }

    @Test
    void findSubEntityTest() {
        testOrg.addSubEntity(academic);
        testOrg.addSubEntity(internal);

        assertEquals(internal, testOrg.findSubEntity("Internal"));
        assertNull(testOrg.findSubEntity("internal"));
        assertEquals(academic, testOrg.findSubEntity("Academic"));
    }

    @Test
    void setNameTest() {
        assertEquals("Test Org", testOrg.getOrgName());
        testOrg.setName("TestOrg1");
        assertEquals("TestOrg1", testOrg.getOrgName());
    }

    @Test
    void getSubEntityListTest() {
        assertTrue(testOrg.getSubEntityList().isEmpty());
        testOrg.addSubEntity(academic);
        assertFalse(testOrg.getSubEntityList().isEmpty());
    }
}