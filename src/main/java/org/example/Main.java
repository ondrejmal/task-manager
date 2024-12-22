package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        System.out.println(ConsoleColors.BLUE + "Task Manager Coderslab" + ConsoleColors.RESET);
        System.out.println("System will now load assigned task files.");

        String filePath = "tasks.csv";
        ArrayList<String[]> fileTasks = loadFiles(filePath);

        System.out.println("Please type name of possible operations to proceed.");
        System.out.println("Select the task from the list below:");
        System.out.println("1) Add");
        System.out.println("2) Remove");
        System.out.println("3) List");
        System.out.println("4) Exit");
        System.out.println("Command in not case sensitive.");

        Scanner scanner = new Scanner(System.in);
        Boolean exit = false;

        while (!exit) {
            String command = scanner.nextLine().trim().toLowerCase();
            System.out.println("Your command is: " + command.toUpperCase());

            switch (command) {
                case "add":
                    addingATask(fileTasks);
                    break;
                case "remove":
                    removingATask(fileTasks);
                    break;
                case "list":
                    listingOfTasks(fileTasks);
                    break;
                case "exit":
                    terminationOfTheProgram();
                    break;
                default:
                    System.out.println("Invalid task selection. Please enter a valid task");
                    break;
            }
        }
        scanner.close();
    }

    public static ArrayList<String[]> loadFiles(String filepath) {
        ArrayList<String[]> fileTasks = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = reader.readLine()) != null)
                fileTasks.add(line.split(","));
        } catch (
                IOException e) {
            System.err.println("Problem with loading the file. File not found. Check if the file exists.");
            e.printStackTrace(System.err);
        }
        return fileTasks;
    }

    public static ArrayList<String[]> addingATask(String fileTasks) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter note:");
        String note = scanner.nextLine();

        System.out.println("Enter date in YYYY-MM-DD format:");
        String date = scanner.nextLine();
        if (!date.matches("\\d{4}-\\d{2}-\\d{2}")){
            System.out.println("Format is not valid. Please enter the value again.");
        }

        System.out.println("Enter true/false value for statement of task completion:");
        String statusInput = scanner.nextLine();
        while (!statusInput.equalsIgnoreCase("true") && !statusInput.equalsIgnoreCase("false")) {
            System.out.println("Invalid input. Please enter true or false:");
            statusInput = scanner.nextLine();
        }
        boolean status = Boolean.parseBoolean(statusInput);

        fileTasks.add(new String[]{note, date, String.valueOf(status)});
        System.out.println("Task added successfully.");
    }

    public static ArrayList<String[]> removingATask(ArrayList<String[]> fileTasks) {
        System.out.println("Current tasks:");
        StringBuilder sb = new StringBuilder();
        int rowNumber = 1;
        for (String[] row : fileTasks) {
            sb.append(rowNumber).append(".");
            sb.append(String.join(",", row)).append("\n");
            rowNumber = rowNumber + 1;
        }
        System.out.println(sb.toString());

        System.out.println("Please choose row number of task to be erased:");
        Scanner scanner = new Scanner(System.in);

        try{
            int taskToBeErased = Integer.parseInt(scanner.nextLine());
            if (taskToBeErased < 1 || taskToBeErased > fileTasks.size()) {
                System.out.println("Invalid input. Please insert valid row number.");
            } else {
                String[] removedTask = fileTasks.remove(taskToBeErased - 1);
                System.out.println("Removed task: " + String.join(", ", removedTask));
            }
        } catch (NumberFormatException e){
            System.out.println("Invalid value. Please enter a valid row number to erase it.");
        }
        scanner.close();
        return fileTasks;
    }

    public static void listingOfTasks(ArrayList<String[]> fileTasks) {
        StringBuilder sb = new StringBuilder();
        int rowNumber = 1;
        for (String[] row : fileTasks) {
            sb.append(rowNumber).append(".");
            sb.append(String.join(",", row)).append("\n");
            rowNumber = rowNumber + 1;
        }
        System.out.println(sb.toString());
    }

    public static void terminationOfTheProgram(ArrayList<String[]> fileTasks, String filePath) {
        try (PrintWriter reader = new PrintWriter(new FileWriter(filePath))) {
            for (String task:fileTasks) {
                writer.println(String.join(", ", task));
            }
            System.out.println("Tasks saved successfully.");

        } catch (IOException e) {
            System.err.println("Problem with saving the file. Unable to locate the folder..");
            e.printStackTrace(System.err);
        }
        System.out.println(ConsoleColors.RED + "Exiting the program." + ConsoleColors.RESET);
        System.exit(0);


    }
}