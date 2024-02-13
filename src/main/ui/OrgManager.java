package ui;

import model.*;

import java.util.LinkedList;
import java.util.Scanner;


// Organization Manager application
public class OrgManager {
    private OrgEntity sus;
    private OrgEntity csss;
    private Scanner input;
    private OrgEntity currentOrg;
    private SubEntity currentSub;

    // EFFECTS: runs the teller application
    public OrgManager() {
        runManager();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runManager() {
        boolean keepGoing = true;
        String command = null;

        init();
        selectOrgEntity();

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
        } else if (command.equals("sl")) {
            seeSubList();
        } else if (command.equals("so")) {
            selectOrgEntity();
        } else {
            System.out.println("Command not valid!");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes organizations
    private void init() {
        sus = new OrgEntity("Science Undergraduate Society");
        csss = new OrgEntity("Computer Science Student Society");
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("Current organization: " + currentOrg.getOrgName());
        System.out.println("Select from:");
        System.out.println("\ta -> add sub-entity");
        System.out.println("\tat -> add task to sub-entity");
        System.out.println("\tr -> remove sub-entity");
        System.out.println("\tsm -> see sub-entity headCount");
        System.out.println("\tsl -> see list of sub-entity");
        System.out.println("\tso -> select different organization");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: create and add a new sub-entity under the organization
    private void addSubEntity() {
        System.out.println("Enter new sub-entity name: ");
        String subName = input.next();
        System.out.println("Enter new sub-entity headcount: ");
        int subHeadCount = input.nextInt();

        SubEntity newSub = new SubEntity(subName, subHeadCount);

        currentOrg.addSubEntity(newSub);
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
            currentOrg.removeSubEntity(currentSub);
            System.out.printf("%s removed from %s successfully!%n", currentSub.getName(), currentOrg.getOrgName());
        }
    }

    // EFFECT: see the headcount of selected sub-entity
    private void seeSubHeadCount() {
        currentSub = selectSubEntity();

        if (currentSub != null) {
            System.out.printf("%s has %s members. %n", currentSub.getName(), currentSub.getHeadCount());
        }
    }

    // EFFECT: see the list of sub-entity under current organization, returns true when list not empty, false otherwise
    private boolean seeSubList() {
        LinkedList<SubEntity> list = currentOrg.getSubEntityList();
        if (list.size() == 0) {
            System.out.println("No sub-entities under current organization!");
            return false;
        } else {
            System.out.printf("List of sub-entities under %s: %n", currentOrg.getOrgName());
            for (SubEntity i : list) {
                System.out.println("\t" + i.getName());
            }
            return true;
        }

    }

    // EFFECT: select organization to be viewed on manager
    private void selectOrgEntity() {
        String selection = "";

        while (!(selection.equals("sus") || selection.equals("csss"))) {
            System.out.println("sus for Science Undergraduate Society");
            System.out.println("csss for Computer Science Student Society");
            selection = input.next();
            selection = selection.toLowerCase();
        }

        if (selection.equals("sus")) {
            currentOrg = sus;
        } else {
            currentOrg = csss;
        }
    }

    // EFFECT: select sub-entity in the organization
    private SubEntity selectSubEntity() {
        String selection = "";
        if (seeSubList()) {
            System.out.println("Enter name of sub-entity to select it: ");
            selection = input.next();

            if (currentOrg.findSubEntity(selection) != null) {
                return currentOrg.findSubEntity(selection);
            } else {
                System.out.printf("Entered sub-entity name not found in %s %n", currentOrg.getOrgName());
                return null;
            }
        } else {
            return null;
        }
    }
}
