package org.example;

import java.util.Scanner;

public class Messages {
    public static final String RESET = "\u001B[0m";
    public static final String BOLD = "\u001B[1m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String BLUE = "\u001B[34m";
    public static final String UNDERLINE = "\u001B[4m";
    public static final String Yellow = "\u001B[33m";
    public static final String CYAN = "\u001B[36m";
    private static final ToDoList list = new ToDoList();
    public static Scanner scan = new Scanner(System.in);

    public static void welcome() {
        System.out.println(BOLD + GREEN + ">>> Welcome Back!" + RESET);
    }

    public static int mainMenu() {
        System.out.println(BOLD + BLUE + "\n>>> Main Menu" + RESET);

        System.out.println("""
                pick and option:
                 1. Show Task List
                 2. Add New Task
                 3. Edit Task
                 4. Delete Task
                 5. Save &""" + RED + " Exit" + RESET);

        int choice = 0;

        while (true) {
            System.out.print("\nEnter Your Choice [1:4]: ");
            try {
                choice = scan.nextInt();
                scan.nextLine();
            } catch (Exception exc) {
                scan.nextLine();
            }

            if (choice >= 1 && choice <= 5) {
                return choice;
            } else {
                System.out.print(RED + "\nInvalid Input" + RESET);
            }
        }
    }

    public static Task newTaskMenu() {
        String title;
        System.out.println(BLUE + BOLD + "\nAdd Task: " + RESET);

        while (true) {
            System.out.print("Enter Title of The task: ");
            title = scan.nextLine();

            if (title.equals("4")) {
                return null;
            }

            if (title.isEmpty()) {
                System.out.println(RED + UNDERLINE + "Title can't be Empty" + RESET);
            } else {
                return new Task(title);
            }
        }
    }

    public static void delTaskMenu() {
        int sr;
        String choice;
        Task task;

        System.out.println(BOLD + BLUE + "\n>>> Delete Menu" + RESET);

        if (!list.displayTasks()) {
            return;
        }

        while (true) {
            System.out.print("\nEnter Serial Number of Task to Delete or -1 for Main Menu: ");

            try {
                sr = scan.nextInt();

                if (sr == -1) {
                    return;
                }

                if (!list.isExist(sr)) {
                    System.out.println("Couldn't Find Any Task With Serial No." + CYAN + sr + RESET + ".");
                    continue;
                } else {
                    task = Database.get(list.getId(sr));
                    list.displayTask(task);
                }

                scan.nextLine();
                break;
            } catch (Exception exc) {
                System.out.print(RED + "\nInvalid Input" + RESET);
                scan.nextLine();
            }
        }

        while (true) {
            System.out.print("Are You Sure You want to delete this task (Y | N): ");
            choice = scan.nextLine().trim().toLowerCase();

            if (choice.equals("y") || choice.equals("n")) {
                if (!choice.equals("n")) {
                    Database.delete(task);
                    System.out.println(GREEN + "Successfully Deleted:" + RESET);
                }
                return;
            } else {
                System.out.print(RED + UNDERLINE + "\nInvalid Choice" + RESET);
            }
        }
    }

    public static void updateMenu() {
        String title;
        String status;
        int sr;
        Task task;

        System.out.println(BOLD + BLUE + "\n>>> Update Menu" + RESET);

        System.out.println(RED + "Note:" + RESET + "\n\t- If You don't want to update Something Just Press" +
                BLUE + " Enter." + RESET +
                "\n\t- 'C' For Mark as" + GREEN + " Completed" + RESET +
                "\n\t- 'P' For Mark as" + Yellow + " Pending" + RESET);

        if (!list.displayTasks()) {
            return;
        }

        while (true) {
            System.out.print("\nEnter Serial Number of Task to update: ");

            try {
                sr = scan.nextInt();
                if (!list.isExist(sr)) {
                    System.out.println("Couldn't Find Any Task With Serial No." + CYAN + sr + RESET + ".");
                    continue;
                } else {
                    task = Database.get(list.getId(sr));
                    list.displayTask(task);
                }

                scan.nextLine();
            } catch (Exception exc) {
                System.out.println(RED + "\nInvalid Input" + RESET);
                scan.nextLine();
                continue;
            }

            System.out.print("Enter New Title: ");
            title = scan.nextLine().trim();

            if (!title.isEmpty()) {
                task.updateTitle(title);
                System.out.println("Title set successfully");
            }
            break;
        }

        while (true) {
            System.out.print("Update Status (C | P): ");
            status = scan.nextLine().trim().toLowerCase();

            if (status.isEmpty()) {
                break;
            }

            if (status.equals("c") || status.equals("p")) {
                task.updateCompleted(status.equals("c"));
                Database.update(task);
                list.displayTask(task);
                return;
            } else {
                System.out.print(RED + "\nInvalid Input" + RESET);
            }
        }
    }

    public static String capitalise(String str) {
        if (str.isEmpty()) {
            return str;
        }
        return Character.toString(str.charAt(0)).toUpperCase() + str.substring(1).toLowerCase();
    }
}