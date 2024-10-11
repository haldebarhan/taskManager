/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package joop;

/**
 *
 * @author aldebaran
 */
public class Main {

    public static void main(String[] args) {
        System.out.print("\033[H\033[2J");  // ANSI code for clear screen
        System.out.flush();
        TaskManager taskManager = new TaskManager();
        taskManager.Start();
    }
}
