package org.example;

public class App {
    public static ToDoList list = new ToDoList();

    public static void main(String[] args) {
        Messages.welcome();

        while (true) {
            int choice = Messages.mainMenu();
            switch (choice) {
                case 1:
                    list.displayTasks();
                    break;
                case 2:
                    Task task = Messages.newTaskMenu();
                    if (task == null) {
                        Database.close();
                        return;
                    } else {
                        Database.save(task);
                    }
                    break;
                case 3:
                    Messages.updateMenu();
                    break;
                case 4:
                    Database.close();
                    return;
            }
        }

    }
}
