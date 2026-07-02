package com.cms.ui;

import com.cms.entity.*;

import java.util.Scanner;

public class ViewerDashboard {
  private final Tournament tournament;
  private final Team team;
  private final Player player;
  private final Squad squad;
  private final Match match;
  private final InningsScoring scoring;
  private final Scanner input;

  public ViewerDashboard(Tournament tournament, Team team, Player player, Squad squad, Match match, InningsScoring scoring, Scanner input) {
    this.tournament = tournament;
    this.team = team;
    this.player = player;
    this.squad = squad;
    this.match = match;
    this.scoring = scoring;
    this.input = input;
  }

  // Main Viewer Dashboard Loop
  public void openViewerDashboard() {
    boolean inViewerDashboard = true;

    while (inViewerDashboard) {
      System.out.println("\n═════════════════════════════════════════════════════");
      System.out.println("  WELCOME TO VIEWER DASHBOARD  ");
      System.out.println("═════════════════════════════════════════════════════");
      System.out.println(" 1. View All Tournaments");
      System.out.println(" 2. View All Participating Teams");
      System.out.println(" 3. View Registered Players");
      System.out.println(" 4. View Team Squads");
      System.out.println(" 5. View Match Schedule & Status");
      System.out.println(" 6. View Live Scorecard ");
      System.out.println(" 0. Exit to Main Menu");
      System.out.println("═════════════════════════════════════════════════════");
      System.out.print(" Choose an option: ");

      int choice = input.nextInt();
      input.nextLine();

      switch (choice) {
        case 1:
          System.out.println("\n---  TOURNAMENT LIST ---");
          tournament.viewAllTournaments();
          break;
        case 2:
          System.out.println("\n--- TEAM LIST ---");
          team.viewAllTeams();
          break;
        case 3:
          System.out.println("\n---PLAYER LIST ---");
          player.viewAllPlayers();
          break;
        case 4:
          System.out.println("\n---  MATCH SCHEDULE ---");
          match.viewAllMatches();
          break;
        case 5:
          System.out.println("\n--- LIVE SCORECARD & STATES ---");
          scoring.displayScorecard();
          break;
        case 0:
          inViewerDashboard = false;
          System.out.println("\n Thank you for using Viewer Mode! Returning to Main Screen...");
          break;
        default:
          System.out.println(" Invalid option! Please choose between 0 and 6.");
      }
    }
  }
}