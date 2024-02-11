package model;

import java.util.LinkedList;

// class for OrgEntities which hold SubEntities
public class OrgEntity {
    private LinkedList<SubEntity> subEntityList;
    private String orgName;

    // EFFECTS: construct an organizational use entity with name
    public OrgEntity(String name) {
        orgName = name;
        subEntityList = new LinkedList<>();
    }

    // MODIFIES: this
    // EFFECTS: add a subgroup under the organizational level entity
    public void addSubEntity(SubEntity sub) {
        subEntityList.addLast(sub);
    }

    // REQUIRES: subEntityList not empty
    // MODIFIES: this
    // EFFECTS: remove a subgroup under the organizational level entity
    public void removeSubEntity(SubEntity sub) {
        subEntityList.remove(sub);
    }

    // REQUIRES: subEntityList not empty
    // EFFECTS: return SubEntity found in list
    public SubEntity findSubEntity(String name) {
        for (SubEntity i : subEntityList) {
            if (i.getName().equals(name)) {
                return i;
            }
        }
        return null;
    }

    // SETTERS
    public void setName(String name) {
        orgName = name;
    }

    // GETTERS
    public String getOrgName() {
        return orgName;
    }

    public LinkedList<SubEntity> getSubEntityList() {
        return subEntityList;
    }

}
