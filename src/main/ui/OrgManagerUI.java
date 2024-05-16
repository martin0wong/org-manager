package ui;

import model.Event;
import model.EventLog;
import model.OrgEntity;
import model.SubEntity;
import model.Task;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

// Represents application's main window frame.

public class OrgManagerUI extends JFrame {
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 400;
    private static final String FILE_DESCRIPTOR = "...file";
    private static final String SCREEN_DESCRIPTOR = "...screen";
    private JInternalFrame controlPanel;

    private OrgEntity org;
    private SubEntity currentSub;
    private static final String JSON_STORE = "./data/OrgManager.json";
    private static final String ADD_ICON = "./data/resources/add-icon.png";
    private static final String REMOVE_ICON = "./data/resources/remove-icon.png";
    private static final String VIEW_ICON = "./data/resources/view-icon.png";
    private static final String SAVE_ICON = "./data/resources/save-icon.png";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    public OrgManagerUI() {
        JFrame frame = null;
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        if (loadOldOrg()) {
            loadOrg();
            frame = new JFrame("Organization Manager - " + org.getOrgName());
        } else {
            org = new OrgEntity(userInput("What is the organization's name?"));
            frame = new JFrame("Organization Manager - " + org.getOrgName());
        }
        addButtonPanel(frame);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new SaveOrgAction());
        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);
    }

    private void addButtonPanel(JFrame frame) {
        frame.setLayout(new GridLayout(2, 3));
        JButton addSubButton = new JButton(new AddSubEntityAction());
        JButton removeSubButton = new JButton(new RemoveSubEntityAction());
        JButton addTaskButton = new JButton(new AddTaskAction());
        JButton seeSubHeadCountButton = new JButton(new SeeSubHeadCountAction());
        JButton seeSubTasksButton = new JButton(new SeeSubTasksAction());
        JButton seeSubsButton = new JButton(new SeeSubsAction());

        addSubButton.setIcon(new ImageIcon(ADD_ICON));
        addTaskButton.setIcon(new ImageIcon(ADD_ICON));
        removeSubButton.setIcon(new ImageIcon(REMOVE_ICON));
        seeSubHeadCountButton.setIcon(new ImageIcon(VIEW_ICON));
        seeSubTasksButton.setIcon(new ImageIcon(VIEW_ICON));
        seeSubsButton.setIcon(new ImageIcon(VIEW_ICON));

        frame.add(addSubButton);
        frame.add(removeSubButton);
        frame.add(addTaskButton);
        frame.add(seeSubHeadCountButton);
        frame.add(seeSubTasksButton);
        frame.add(seeSubsButton);
    }

    // Represents action to be taken when user wants to add a new sub entity to the organization.
    private class AddSubEntityAction extends AbstractAction {
        AddSubEntityAction() {
            super("Add Sub-Entity");
        }

        // MODIFIES: this
        // EFFECTS: create and add a new sub-entity under the organization
        @Override
        public void actionPerformed(ActionEvent evt) {
            String subName = userInput("Enter new sub-entity name:");
            int subHeadCount = Integer.parseInt(userInput("Enter new sub-entity headcount:"));

            SubEntity newSub = new SubEntity(subName, subHeadCount);
            org.addSubEntity(newSub);

            String addSubMessage = subName + " has been created as a sub-entity and added to list successfully!";
            JOptionPane.showMessageDialog(null, addSubMessage);
        }
    }

    // Represents action to be taken when user wants to remove a sub entity from the organization.
    private class RemoveSubEntityAction extends AbstractAction {
        RemoveSubEntityAction() {
            super("Remove Sub-Entity");
        }

        // MODIFIES: this
        // EFFECTS: remove the sub-entity under the organization
        @Override
        public void actionPerformed(ActionEvent evt) {
            currentSub = selectSubEntity();
            if (currentSub != null) {
                org.removeSubEntity(currentSub);

                String removeSubMessage = currentSub.getName() + " removed from " + org.getOrgName() + " successfully!";
                JOptionPane.showMessageDialog(null, removeSubMessage);
            }
        }
    }

    // Represents action to be taken when user wants to add a new task to a sub entity under the organization.
    private class AddTaskAction extends AbstractAction {
        AddTaskAction() {
            super("Add Task to Sub-Entity");
        }

        // MODIFIES: this
        // EFFECTS: create and add a new task for the sub-entity
        @Override
        public void actionPerformed(ActionEvent evt) {
            currentSub = selectSubEntity();
            if (currentSub != null) {
                String taskName = userInput("Enter new task:");
                Task newTask = new Task(taskName);
                currentSub.addTask(newTask);

                String addTaskMessage = taskName + " has been created and assigned to " + currentSub.getName();
                JOptionPane.showMessageDialog(null, addTaskMessage);
            }
        }
    }

    // Represents action to be taken when user wants to see the headcount of a sub entity under the organization.
    private class SeeSubHeadCountAction extends AbstractAction {
        SeeSubHeadCountAction() {
            super("See Sub-Entity Headcount");
        }

        // EFFECT: see the headcount of selected sub-entity
        @Override
        public void actionPerformed(ActionEvent evt) {
            currentSub = selectSubEntity();
            if (currentSub != null) {
                String subHeadCount = currentSub.getName() + " has " + currentSub.getHeadCount() + " members.";
                JOptionPane.showMessageDialog(null, subHeadCount);
            }
        }
    }

    // Represents action to be taken when user wants to see the tasks for a sub entity under the organization.
    private class SeeSubTasksAction extends AbstractAction {
        SeeSubTasksAction() {
            super("See Sub-Entity Tasks");
        }

        // EFFECT: see the headcount of selected sub-entity
        @Override
        public void actionPerformed(ActionEvent evt) {
            currentSub = selectSubEntity();
            if (currentSub != null) {
                String subTasks = currentSub.getName() + " has " + currentSub.getTaskList().size() + " tasks. \n";
                for (Task t : currentSub.getTaskList()) {
                    subTasks += "\t" + t.getName() + "\n";
                }
                JOptionPane.showMessageDialog(null, subTasks);
            }
        }
    }

    // Represents action to be taken when user wants to see the sub entities under the organization.
    private class SeeSubsAction extends AbstractAction {
        SeeSubsAction() {
            super("See List of Sub-Entity");
        }

        // EFFECT: see the list of sub-entity under current organization
        @Override
        public void actionPerformed(ActionEvent evt) {
            LinkedList<SubEntity> list = org.getSubEntityList();
            if (list.size() == 0) {
                JOptionPane.showMessageDialog(null, systemOutput(6));
            } else {
                String subList = "List of sub-entities under " + org.getOrgName() + "\n";
                for (SubEntity i : list) {
                    subList += "\t" + i.getName() + "\n";
                }
                JOptionPane.showMessageDialog(null, subList);
            }
        }
    }

    // EFFECT: select sub-entity in the organization when there are sub entities, otherwise print error message
    private SubEntity selectSubEntity() {
        String selection = "";
        if (org.getSubEntityList().size() > 0) {
            selection = userInput("Enter name of sub-entity to select it: ");

            if (org.findSubEntity(selection) != null) {
                return org.findSubEntity(selection);
            } else {
                JOptionPane.showMessageDialog(null, systemOutput(5), "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        } else {
            return null;
        }
    }

    // EFFECTS: return a boolean value to indicate whether user want to load saved org
    private boolean loadOldOrg() {
        int option = JOptionPane.showOptionDialog(
                OrgManagerUI.this,
                "Load saved Organization from before?",
                "Loading prompt", JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE, null, null,
                null);
        if (option == 0) {
            return true;
        } else {
            return false;
        }
    }

    // EFFECTS: return the string entered by user (helper function to replace console input)
    private String userInput(String prompt) {
        String input = javax.swing.JOptionPane.showInputDialog(prompt);
        return input;
    }

    // EFFECTS: return the string printed by system (helper function to replace system println)
    private String systemOutput(int messageNum) {
        switch (messageNum) {
            case 1:
                return "Saved " + org.getOrgName() + " to " + JSON_STORE;
            case 2:
                return "Unable to write to file: " + JSON_STORE;
            case 3:
                return "Loaded " + org.getOrgName() + " from " + JSON_STORE;
            case 4:
                return "Unable to read from file: " + JSON_STORE;
            case 5:
                return "Entered sub-entity name not found in " + org.getOrgName();
            case 6:
                return "No sub-entities under current organization!";
        }
        // placeholder for syntax, system will call existing message internally
        return "";
    }

    // Represents action to be taken when prompting the user to save the organization.
    private class SaveOrgAction extends WindowAdapter {
        // EFFECT: saves the Org to file
        @Override
        public void windowClosing(WindowEvent evt) {
            int option = JOptionPane.showOptionDialog(
                    OrgManagerUI.this,
                    "Save organization?",
                    "Saving prompt", JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE, new ImageIcon(SAVE_ICON), null,
                    null);
            if (option == 0) {
                try {
                    jsonWriter.open();
                    jsonWriter.write(org);
                    jsonWriter.close();
                    JOptionPane.showMessageDialog(null, systemOutput(1), "Saving Org", JOptionPane.PLAIN_MESSAGE);
                    printLog();
                    System.exit(0);
                } catch (FileNotFoundException e) {
                    JOptionPane.showMessageDialog(null, systemOutput(2), "Saving Org", JOptionPane.ERROR_MESSAGE);
                    printLog();
                    System.exit(0);
                }
            } else {
                printLog();
                System.exit(0);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: loads Org from file
    private void loadOrg() {
        try {
            org = jsonReader.read();
            JOptionPane.showMessageDialog(null, systemOutput(3), "Loading Org", JOptionPane.PLAIN_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, systemOutput(4), "Loading Org", JOptionPane.ERROR_MESSAGE);
        }
    }

    // EFFECTS: print out EventLog to console
    private void printLog() {
        for (Event next : EventLog.getInstance()) {
            System.out.println(next.toString() + "\n");
        }
    }

    // starts the application
    public static void main(String[] args) {
        new OrgManagerUI();
    }
}