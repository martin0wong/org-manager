# Martin's Personal Project - Organization Structure Manager

## What will the application do?

- Visualizing the structure of the organisation from user input
  - Add / Delete entities under existing structure

- Keep track of headcount in different entities under the organisation
  - Plus / Minus headcount for easier macro management

- Add statistics under entities to show help analyze an entity
  - *examples:* 
    - police - crime numbers/types by districts
    - government - group/department efficiency (kpi satisfaction%, etc)

## Who will use it?

Anyone! If you have a need to manage your organisation (e.g. online groups), 
this tool will help you.

## Why is this project of interest to you?

Previous experience from online groups sparked my interest on this project.
Structures were hard to keep track of as stuff keeps changing whenever leadership feels like it, 
confusing the regular members and in some cases command members as well.\
Hopefully, with this project, such messy structure will be much easier to
follow by members in such groups. 
While also improving transparency of organisations by making data more accessible, bridging digital divide.

## User Stories
- As a user, I want to be able to add a division to my department.
- As a user, I want to be able to delete a division from my department.
- As a user, I want to be able to see the list of division(s) in my department.
- As a user, I want to be able to see the list of task(s) assigned to my one of my division.
- As a user, I want to be able to select a division and see the total member count of it.
- As a user, I want to be able to assign a task to the division I selected.
- As a user, I want to be given the option to save my organization and it's divisions.
- As a user, I want to be given the option for load my organization and it's divisions.

## Instructions for Grader
Enter the information as prompted for all functions.
### Add multiple Xs to a Y
Click the add button for new sub-entity to add a new sub-entity under the organization.\
Click the add button for new tasks to add a new task to an existing sub-entity.

### Loading and Saving
Users will be prompted to load and or save their organization when opening/closing the software.

### Visual Component
All visual components are observable once entering the main screen, saving prompt has its own icon as well.

## Credit
All icons used are from [uxwing](https://uxwing.com/license/), credit was not necessary but given here.

## Phase 4 - Task 2
Sample log message with the following actions taken:
- Loading from JSON
- Adding new sub-entity named Academic with 10 headcount
- Adding new task named Host Midterm Review Session to Academic
- Seeing headcount of sub entity Internal
- Seeing list of tasks for sub entity External
- Removing sub-entity named External from the organization
- Saving organization to JSON
> Sun Mar 31 23:07:00 PDT 2024
Task: Another task is added to Internal\
Sun Mar 31 23:07:00 PDT 2024
Sub entity: Internal is added to sus\
Sun Mar 31 23:07:00 PDT 2024
Task: PARTY! is added to External\
Sun Mar 31 23:07:00 PDT 2024
Sub entity: External is added to sus\
Sun Mar 31 23:07:08 PDT 2024
Sub entity: Academic is added to sus\
Sun Mar 31 23:07:12 PDT 2024
List of sub entities retrieved\
Sun Mar 31 23:07:31 PDT 2024
Task: Host Midterm Review Session is added to Academic\
Sun Mar 31 23:07:35 PDT 2024
List of sub entities retrieved\
Sun Mar 31 23:07:37 PDT 2024
Headcount of Internal retrieved\
Sun Mar 31 23:07:39 PDT 2024
List of sub entities retrieved\
Sun Mar 31 23:07:41 PDT 2024
List of tasks of External retrieved\
Sun Mar 31 23:07:41 PDT 2024
List of tasks of External retrieved\
Sun Mar 31 23:07:45 PDT 2024
List of sub entities retrieved\
Sun Mar 31 23:07:47 PDT 2024
List of sub entities retrieved\
Sun Mar 31 23:07:52 PDT 2024
Sub entity: External is removed from sus\
Sun Mar 31 23:07:55 PDT 2024
Saving sus to JSON\
Sun Mar 31 23:07:55 PDT 2024
Saving Internal to JSON\
Sun Mar 31 23:07:55 PDT 2024
Task: Another task saved to JSON\
Sun Mar 31 23:07:55 PDT 2024
Sub entity: Internal saved to JSON\
Sun Mar 31 23:07:55 PDT 2024
Saving Academic to JSON\
Sun Mar 31 23:07:55 PDT 2024
Task: Host Midterm Review Session saved to JSON\
Sun Mar 31 23:07:55 PDT 2024
Sub entity: Academic saved to JSON\
Sun Mar 31 23:07:55 PDT 2024
sus saved to JSON



