package com.cms.ui;

import com.cms.entity.Squad;

import java.util.Scanner;

public class ManagerDashboard {
  private final Squad squad;
  private final Scanner input;

  public ManagerDashboard(Squad squad, Scanner input) {
    this.squad = squad;
    this.input = input;
  }

  public void openManagerDashboard() {
    boolean inManagerDashboard = true;

    while (inManagerDashboard) {
      System.out.println("\n═════════════════════════════════════════════════════");
      System.out.println(" WELCOME TO TEAM MANAGER DASHBOARD ");
      System.out.println("═════════════════════════════════════════════════════");
      System.out.println(" 1. Build & Manage Team Squad");
      System.out.println(" 2. View Squad");
      System.out.println(" 0. Logout & Exit to Main Menu");
      System.out.println("═════════════════════════════════════════════════════");
      System.out.print(" Choose an option: ");

      int choice = input.nextInt();
      input.nextLine();

      switch (choice) {
        case 1:
          squad.buildSquad();
          break;
        case 2:
          squad.viewSquad();
          break;
        case 0:
          inManagerDashboard = false;
          System.out.println("\n Logged out from Manager Dashboard!");
          break;
        default:
          System.out.println(" Invalid option! Please choose 0 or 1.");
      }
    }
  }
}