package ui;

import model.*;

import java.util.LinkedList;
import java.util.Scanner;


// Bank teller application
public class OrgManager {
    private OrgEntity sus;
    private OrgEntity csss;
    private Scanner input;
    private OrgEntity currentOrg;
    private SubEntity currentSubOrg;

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
        System.out.println("\nSelect from:");
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

        SubEntity newSub = new SubEntity(subName,subHeadCount);

        currentOrg.addSubEntity(newSub);
        System.out.println(subName + "has been created as a sub-entity and added to list successfully!");
    }

    // MODIFIES: this
    // EFFECTS: create and add a new task for the sub-entity
    private void addTaskToSubEntity() {
        currentSubOrg = selectSubEntity();
        if (currentSubOrg != null) {
            System.out.println("Enter new task");
            String taskName = input.next();

            Task newTask = new Task(taskName);

            currentSubOrg.addTask(newTask);
            System.out.println(taskName + "has been created and assigned to " + currentSubOrg.getName());
        }
    }

    // MODIFIES: this
    // EFFECTS: remove the sub-entity under the organization
    private void removeSubEntity() {
        System.out.println("\nEnter sub-entity name: ");
        String subName = input.next();
        SubEntity sub = currentOrg.findSubEntity(subName);
        if (sub != null) {
            currentOrg.removeSubEntity(sub);
        } else {
            System.out.println("Sub-entity with given name cannot be found!");
        }

    }

    // EFFECT: see the headcount of selected sub-entity
    private int seeSubHeadCount() {
        System.out.println("Enter sub-entity name: ");
        String subName = input.next();

        if (currentOrg.findSubEntity(subName) != null) {
            SubEntity sub = currentOrg.findSubEntity(subName);
            return sub.getHeadCount();
        } else {
            System.out.println("Entered sub-entity name not found in " + currentOrg.getOrgName());
            return -1;
        }
    }

    // EFFECT: see the list of sub-entity under current organization
    private void seeSubList() {
        LinkedList<SubEntity> list = currentOrg.getSubEntityList();
        if (list.size() < 1) {
            System.out.println("No sub-entity under current organization!");
        } else {
            for (SubEntity i : list) {
                System.out.println(i.getName());
            }
        }

    }

    // EFFECT: select organization to be viewed on manager
    private OrgEntity selectOrgEntity() {
        String selection = "";  // force entry into loop

        while (! (selection.equals("sus") || selection.equals("csss"))) {
            System.out.println("sus for Science Undergraduate Society");
            System.out.println("csss for Computer Science Student Society");
            selection = input.next();
            selection = selection.toLowerCase();
        }

        if (selection.equals("sus")) {
            currentOrg = sus;
            return sus;
        } else {
            currentOrg = csss;
            return csss;
        }
    }

    // EFFECT: select sub-entity in the organization
    private SubEntity selectSubEntity() {
        String selection = "";  // force entry into loop
        LinkedList<SubEntity> list = currentOrg.getSubEntityList();

        for (SubEntity i : list) {
            System.out.println(i.getName());
        }

        System.out.println("Enter name of sub-entity to select it: ");
        selection = input.next();

        if (currentOrg.findSubEntity(selection) != null) {
            return currentOrg.findSubEntity(selection);
        } else {
            System.out.println("Entered sub-entity name not found in " + currentOrg.getOrgName());
            return null;
        }
    }
}
