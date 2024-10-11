package joop;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class TaskManager {

    private final LinkedList<Task> tasks;
    private int score;

    public TaskManager() {
        this.tasks = new LinkedList<>();
        this.score = 0;
    }

    public void DisplayPlayerInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nYou have ").append(score).append(" points");
        System.out.println(sb.toString());
    }

    public void ListTaskNames() {
        int index = 1;
        for (Task task : tasks) {
            StringBuilder sb = new StringBuilder();
            sb.append(index).append(". ").append(task.GetDetails());
            System.out.println(sb.toString());
            index++;
        }
    }

    public void ListTaskDetails() {
        int index = 1;
        StringBuilder sb = new StringBuilder();
        System.out.println("Task List");
        for (Task task : tasks) {
            sb.append(index).append(". ").append(task.GetDetails());
        }
    }

    private void createSimpleTask() {
        String name = GetInput("What is the name of the task ? ");
        String description = GetInput("What is a short description of it? ");
        int points = getIntInput("What is the amount of points associated with this task? ");
        SimpleTask simpleTask = new SimpleTask(name, description, points, false);
        AddTask(simpleTask);
    }

    private void createDailyTask() {
        String name = GetInput("What is the name of the task ? ");
        String description = GetInput("What is a short description of it? ");
        int points = getIntInput("What is the amount of points associated with this task? ");

        DailyTask dailyTask = new DailyTask(name, description, points);
        AddTask(dailyTask);
    }

    private void createCheckListTask() {
        String name = GetInput("What is the name of the task ? ");
        String description = GetInput("What is a short description of it? ");
        int points = getIntInput("What is the amount of points associated with this task? ");
        int target = getIntInput("How many times does this task need to be accomplished for a bonus? ");
        int bonus = getIntInput("What is the bonus for accomplishing it that many times? ");
        CheckListTask checkListTask = new CheckListTask(name, description, points, 0, target, bonus);
        AddTask(checkListTask);
    }

    private String GetInput(String prompt) {
        Scanner myScanner = new Scanner(System.in);
        System.out.print(prompt);
        return myScanner.nextLine();
    }

    private int getIntInput(String prompt) {
        while (true) {
            Scanner myScanner = new Scanner(System.in);
            System.out.print(prompt);
            String input = myScanner.nextLine();
            try {
                int intInput = Integer.parseInt(input);
                return intInput;
            } catch (IllegalAccessError e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    private void AddTask(Task task) {
        tasks.add(task);
    }

    public int GetScore() {
        return score;
    }

    public void SetScore(int score) {
        this.score = score;
    }

    public void RecordEvent() {
        List<Task> uncompletedTasks = tasks.stream().filter(task -> !task.IsCompleted()).collect(Collectors.toList());
        if (tasks.isEmpty() || uncompletedTasks.isEmpty()) {
            System.out.println("No task registered.");
        } else {

            System.out.println("The uncompleted tasks are:");
            for (int i = 0; i < uncompletedTasks.size(); i++) {
                StringBuilder sb = new StringBuilder();
                sb.append(i + 1).append(". ").append(uncompletedTasks.get(i).GetShortName());
                System.out.println(sb.toString());
            }
            int answer = getIntInput("\nWhich task did you accomplish?");
            if (answer < 0 || answer > uncompletedTasks.size()) {
                System.out.println("Invalid input. Please enter a valid number corresponding to an uncompleted task.");
            }

            Task selectedTask = uncompletedTasks.get(answer - 1);
            selectedTask.recordEvent();
            int earnedPoint = selectedTask.getPoint();
            score += earnedPoint;
            String congratulationMessage = String.format("Congratulation! you have earned %d points", earnedPoint);
            String Message = String.format("You now have %d points", score);
            System.out.println(congratulationMessage);
            System.out.println(Message);
        }

    }

    public void CreateTask() {
        while (true) {
            Menu.TaskMenu();
            int choice = getIntInput("");
            switch (choice) {
                case 1 -> {
                    createSimpleTask();
                    return;
                }
                case 2 -> {
                    createDailyTask();
                    return;
                }
                case 3 -> {
                    createCheckListTask();
                    return;
                }

                default ->
                    System.out.println("Invalid choice. Please select a valid Task type.");
            }
        }
    }

    public void Start() {
        int menu = 0;
        while (menu != 6) {
            DisplayPlayerInfo();
            Menu.DisplayMenu();
            int choice = getIntInput("");
            switch (choice) {
                case 1 ->
                    CreateTask();
                case 2 ->
                    ListTaskNames();
                case 3 ->
                    SaveTasks();

                case 4 -> {
                    CompletableFuture<Void> loadTaskFuture = LoadTasks();
                    loadTaskFuture.join();
                }

                case 5 -> {
                    RecordEvent();
                }
                case 6 -> {
                    System.out.println("Goodye");
                    System.exit(0);
                }
                default -> {
                    System.err.println("Invalid input. Please enter a valid number.");
                }
            }
        }
    }

    public void SaveTasks() {
        String fileName = GetInput("What is the filename for the taks file? ");
        String taskDirectory = Paths.get(System.getProperty("user.dir"), "TaskFiles").toString();

        File directory = new File(taskDirectory);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        String filePath = Paths.get(taskDirectory, fileName).toString();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(String.valueOf(GetScore()));
            writer.newLine();

            for (Task task : tasks) {
                writer.write(task.GetStringRepresentation());
                writer.newLine();
            }
        } catch (IOException ex) {
            System.out.println("An error occurred while saving goals: " + ex.getMessage());
        }
    }

    public CompletableFuture<Void> LoadTasks() {
        return CompletableFuture.runAsync(() -> {
            String fileName = GetInput("What is the filename for the task?");
            String filePath = Paths.get(System.getProperty("user.dir"), "TaskFiles", fileName).toString();
            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("File not found. Please check the filename and try again.");
                return;
            }
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String scoreLine = reader.readLine();
                if (scoreLine != null) {
                    int parsedScore = Integer.parseInt(scoreLine);
                    SetScore(parsedScore);
                }

                String line;
                while ((line = reader.readLine()) != null) {
                    try {
                        Task task = TaskFactory.createTaskFromString(line);
                        if (task != null) {
                            AddTask(task);
                        }
                    } catch (IllegalArgumentException ex) {
                        System.out.println("Error loading goal: " + ex.getMessage());
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("File not found. Please check the filename and try again.");
            } catch (IOException e) {
                System.out.println("An error occurred while loading goals: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("File format is incorrect. Please ensure the file is not corrupted.");
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        });
    }
}
