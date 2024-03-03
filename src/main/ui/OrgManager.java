package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;


// Organization Manager application
public class OrgManager {
    private static final String JSON_STORE = "./data/OrgManager.json";
    private OrgEntity org;
    private Scanner input;
    private SubEntity currentSub;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the teller application
    public OrgManager() throws FileNotFoundException {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runManager();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runManager() {
        boolean keepGoing = true;
        String command = null;

        System.out.println("Load Organization? (y/n)");
        command = input.next();
        command = command.toLowerCase();
        if (command.equals("y")) {
            loadOrg();
        } else {
            org = createOrg();
        }


        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            addSubEntity();
        } else if (command.equals("at")) {
            addTaskToSubEntity();
        } else if (command.equals("r")) {
            removeSubEntity();
        } else if (command.equals("sm")) {
            seeSubHeadCount();
        } else if (command.equals("stl")) {
            seeSubTasks();
        } else if (command.equals("sl")) {
            seeSubList();
        } else if (command.equals("s")) {
            saveOrg();
        } else if (command.equals("l")) {
            loadOrg();
        } else {
            System.out.println("Command not valid!");
        }
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("Current organization: " + org.getOrgName());
        System.out.println("Select from:");
        System.out.println("\ta -> add sub-entity");
        System.out.println("\tat -> add task to sub-entity");
        System.out.println("\tr -> remove sub-entity");
        System.out.println("\tsm -> see sub-entity headCount");
        System.out.println("\tstl -> see sub-entity task list");
        System.out.println("\tsl -> see list of sub-entity");
        System.out.println("\ts -> save organization to file");
        System.out.println("\tl -> load organization from file");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: create the organization to initiate the manager
    private OrgEntity createOrg() {
        System.out.println("Enter new Organization name: ");
        String orgName = input.next();
        return new OrgEntity(orgName);
    }

    // MODIFIES: this
    // EFFECTS: create and add a new sub-entity under the organization
    private void addSubEntity() {
        System.out.println("Enter new sub-entity name: ");
        String subName = input.next();
        System.out.println("Enter new sub-entity headcount: ");
        int subHeadCount = input.nextInt();

        SubEntity newSub = new SubEntity(subName, subHeadCount);

        org.addSubEntity(newSub);
        System.out.printf("%s has been created as a sub-entity and added to list successfully!%n", subName);
    }

    // MODIFIES: this
    // EFFECTS: create and add a new task for the sub-entity
    private void addTaskToSubEntity() {
        currentSub = selectSubEntity();
        if (currentSub != null) {
            System.out.println("Enter new task:");
            String taskName = input.next();

            Task newTask = new Task(taskName);

            currentSub.addTask(newTask);
            System.out.printf("%s has been created and assigned to %s %n", taskName, currentSub.getName());
        }
    }

    // MODIFIES: this
    // EFFECTS: remove the sub-entity under the organization
    private void removeSubEntity() {
        currentSub = selectSubEntity();
        if (currentSub != null) {
            org.removeSubEntity(currentSub);
            System.out.printf("%s removed from %s successfully!%n", currentSub.getName(), org.getOrgName());
        }
    }

    // EFFECT: see the headcount of selected sub-entity
    private void seeSubHeadCount() {
        currentSub = selectSubEntity();

        if (currentSub != null) {
            System.out.printf("%s has %s members. %n", currentSub.getName(), currentSub.getHeadCount());
        }
    }

    // EFFECT: see the headcount of selected sub-entity
    private void seeSubTasks() {
        currentSub = selectSubEntity();

        if (currentSub != null) {
            System.out.printf("%s has %s tasks. %n", currentSub.getName(), currentSub.getTaskList().size());
            for (Task t : currentSub.getTaskList()) {
                System.out.println("\t" + t.getName());
            }
        }
    }

    // EFFECT: see the list of sub-entity under current organization, returns true when list not empty, false otherwise
    private boolean seeSubList() {
        LinkedList<SubEntity> list = org.getSubEntityList();
        if (list.size() == 0) {
            System.out.println("No sub-entities under current organization!");
            return false;
        } else {
            System.out.printf("List of sub-entities under %s: %n", org.getOrgName());
            for (SubEntity i : list) {
                System.out.println("\t" + i.getName());
            }
            return true;
        }
    }

    // EFFECT: select sub-entity in the organization
    private SubEntity selectSubEntity() {
        String selection = "";
        if (seeSubList()) {
            System.out.println("Enter name of sub-entity to select it: ");
            selection = input.next();

            if (org.findSubEntity(selection) != null) {
                return org.findSubEntity(selection);
            } else {
                System.out.printf("Entered sub-entity name not found in %s %n", org.getOrgName());
                return null;
            }
        } else {
            return null;
        }
    }

    // EFFECTS: saves the workroom to file
    private void saveOrg() {
        try {
            jsonWriter.open();
            jsonWriter.write(org);
            jsonWriter.close();
            System.out.println("Saved " + org.getOrgName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadOrg() {
        try {
            org = jsonReader.read();
            System.out.println("Loaded " + org.getOrgName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

}
