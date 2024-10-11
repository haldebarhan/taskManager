package joop;

public class Menu {

    public static void DisplayMenu() {
        System.out.println("Task Options:");
        System.out.println("1. Create New Task");
        System.out.println("2. List Tasks");
        System.out.println("3. Save Tasks");
        System.out.println("4. Load Tasks");
        System.out.println("5. Record Event");
        System.out.println("6. Quit");
        System.out.print("Select a choice from the menu: ");
    }

    public static void TaskMenu() {
        System.out.println("\nThe types of Task are:");
        System.out.println("1. Simple Task");
        System.out.println("2. daily Task");
        System.out.println("3. CheckList Task");
        System.out.print("Which type of Task would you like to create? ");
    }
}
