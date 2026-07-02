package com.cms.ui;

import com.cms.entity.Auction;
import com.cms.entity.Match;
import com.cms.entity.Squad;

import java.util.Scanner;

public class PlayerDashboard {
  private final Match match;
  private final Squad squad;
  private final Auction auction;
  private final Scanner input;


  public PlayerDashboard(Match match, Squad squad, Auction auction, Scanner input) {
    this.match = match;
    this.squad = squad;
    this.auction = auction;
    this.input = input;

  }

  public void openPlayerDashboard() {
    boolean inPlayerDashboard = true;

    while (inPlayerDashboard) {
      System.out.println("\n═════════════════════════════════════════════════════");
      System.out.println("          WELCOME TO PLAYER DASHBOARD ");
      System.out.println("═════════════════════════════════════════════════════");
      System.out.println(" 1.  View Auction results");
      System.out.println(" 2.  View My Team Squad");
      System.out.println(" 3.  View Match Schedule");
      System.out.println(" 0.  Logout & Exit to Main Menu");
      System.out.println("═════════════════════════════════════════════════════");
      System.out.print(" Choose an option: ");

      int choice = input.nextInt();
      input.nextLine();

      switch (choice) {
        case 1:
          auction.viewAucResults();
          break;
        case 2:
          squad.viewSquad();
          break;
        case 3:
          match.viewAllMatches();
          break;

        case 0:
          inPlayerDashboard = false;
          System.out.println("\n Logged out from Player Dashboard!");
          break;
        default:
          System.out.println(" Invalid option! Please choose between 0 and 3.");
      }
    }
  }

}