package org.example;

import java.util.List;

public class ToDoList {
    private static int total;
    private static int completed;
    private final String format;
    private List<Task> tasks;

    public ToDoList() {
        format = "%-6s %-57s %-15s %-18s %-10s";
    }

    public static int getTotal() {
        return total;
    }

    public static int getCompleted() {
        return completed;
    }

    public void updateTotalAndCompleted() {
        total = this.tasks.size();

        completed = 0;
        for (Task task : this.tasks) {
            if (task.isCompleted()) {
                completed++;
            }
        }
    }

    public Boolean isExist(int sr) {
        this.updateTasks();
        return tasks.get(sr - 1).getSerialNo() == sr;
    }

    public void updateTasks() {
        this.tasks = Database.get();
        updateTotalAndCompleted();
    }

    private void displayHeader() {
        int total = ToDoList.getTotal();
        int completed = ToDoList.getCompleted();
        int notCompleted = total - completed;

        System.out.println("\n" + Messages.UNDERLINE + " ".repeat(105) + Messages.RESET);

        System.out.println("Total Tasks: " + total + " ".repeat(50)
                + "(Completed: " + completed
                + (notCompleted > 0 ? " ".repeat(10) + Messages.RED + "Not Completed: " + notCompleted + Messages.RESET : "")
                + ")");

        System.out.println(Messages.UNDERLINE + " ".repeat(105) + Messages.RESET);
    }

    public Boolean displayTasks() {
        this.updateTasks();

        if (!tasks.isEmpty()) {
            displayHeader();
            System.out.printf((format) + "%n", "NUM", "TITLE", "STATUS", "DATE", "TIME");
            System.out.printf((format) + "%n", "---", "-----", "------", "----", "----");
        } else {
            System.out.println(Messages.RED + "No Tasks Found" + Messages.RESET);
            return false;
        }

//        for (Task task : tasks) {
//            displayTask(task);
//        }

        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            task.setSerialNo(i + 1);
            displayTask(task);
        }
        return true;
    }

    public int getId(int sr) {
        for (Task task : tasks) {
            if (task.getSerialNo() == sr) {
                return task.getId();
            }
        }

        return -1;
    }

    public void displayTask(Task task) {
        System.out.printf((format) + "%n", Messages.CYAN + task.getSerialNo() + "." + Messages.RESET, task.getTitle(),
                (task.isCompleted() ? "Completed" : "Pending"),
                task.getDate(), task.getTime());
    }
}
