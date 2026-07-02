package com.cms.ui;

import com.cms.entity.*;

import java.util.Scanner;

public class AdminDashboard {
  private final Tournament tournament;
  private final Team team;
  private final Player player;
  private final Auction auction;
  private final Squad squad;
  private final Match match;
  private final InningsScoring scoring;
  private final Staff staff;
  private final Scanner input;

  public AdminDashboard(Tournament tournament, Team team, Player player, Auction auction, Squad squad, Match match, InningsScoring scoring, Staff staff, Scanner input) {
    this.tournament = tournament;
    this.team = team;
    this.player = player;
    this.auction = auction;
    this.squad = squad;
    this.match = match;
    this.scoring = scoring;
    this.staff = staff;
    this.input = input;
  }

  public void openAdminDashboard() {
    boolean inAdminDashboard = true;

    while (inAdminDashboard) {
      System.out.println("\n═════════════════════════════════════════════════════");
      System.out.println("     WELCOME TO ADMIN DASHBOARD (Full Access) ");
      System.out.println("═════════════════════════════════════════════════════");
      System.out.println(" 1.  Tournament Management (Add/View/Update/Delete)");
      System.out.println(" 2.  Team Management (Add/View/Update/Delete)");
      System.out.println(" 3.  Player Management (Add/iew/Update/Delete)");
      System.out.println(" 4.  Auction Management (Create/Conduct/History)");
      System.out.println(" 5.  Squad Management (Build/View Squad)");
      System.out.println(" 6.  Match Management (Schedule/View Matches)");
      System.out.println(" 7.  Innings & Live Scoring (Start/Record Innings)");
      System.out.println(" 8.  Staff & Contracts (Add/Assign Staff)");
      System.out.println(" 0.  Logout (Return to Main Menu)");
      System.out.println("═════════════════════════════════════════════════════");
      System.out.print(" Choose an choice: ");

      int choice = input.nextInt();
      input.nextLine();

      switch (choice) {
        case 1:
          tournamentMenu();
          break;
        case 2:
          teamMenu();
          break;
        case 3:
          playerMenu();
          break;
        case 4:
          auctionMenu();
          break;
        case 5:
          squadMenu();
          break;
        case 6:
          matchMenu();
          break;
        case 7:
          scoringMenu();
          break;
        case 8:
          staffMenu();
          break;
        case 0:
          inAdminDashboard = false;
          System.out.println("\n Admin logged out successfully! Returning to Man Screen...");
          break;
        default:
          System.out.println("Invalid option! Please choose between 0 and 8.");
      }
    }
  }

  private void tournamentMenu() {
    boolean loop = true;
    while (loop) {
      System.out.println("\n---  TOURNAMENT MANAGEMENT ---");
      System.out.println("1. Create / Add Tournament");
      System.out.println("2. View All Tournaments");
      System.out.println("3. Update Tournament Details");
      System.out.println("4. Delete Tournament");
      System.out.println("0. Back to Main Dashboard");
      System.out.print(" Choice: ");
      int subChoice = input.nextInt();
      input.nextLine();

      switch (subChoice) {
        case 1:
          tournament.addTournament();
          break;
        case 2:
          tournament.viewAllTournaments();
          break;
        case 3:
          tournament.updateTournament();
          break;
        case 4:
          tournament.deleteTournament();
          break;
        case 0:
          loop = false;
          break;
        default:
          System.out.println("Invalid Choice!");
      }
    }
  }

  private void teamMenu() {
    boolean loop = true;
    while (loop) {
      System.out.println("\n--- TEAM MANAGEMENT ---");
      System.out.println("1. Add New Team");
      System.out.println("2. View All Teams");
      System.out.println("3. Update Team Details");
      System.out.println("4. Delete Team");
      System.out.println("5. Link Manager to Team");
      System.out.println("0. Back  to Main Dashboard");
      System.out.print("Enter  Choice: ");
      int subChoice = input.nextInt();
      input.nextLine();

      switch (subChoice) {
        case 1:
          team.addTeam();
          break;
        case 2:
          team.viewAllTeams();
          break;
        case 3:
          team.updateTeam();
          break;
        case 4:
          team.deleteTeams();
          break;
        case 5:
          System.out.print("Enter Team ID: ");
          int tIdLink = input.nextInt();
          System.out.print("Enter Manager's User ID (from users table): ");
          int uIdLink = input.nextInt();
          input.nextLine();
          team.linkManagerToTeam(tIdLink, uIdLink);
          break;
        case 0:
          loop = false;
          break;
        default:
          System.out.println("Invalid Choice!");
      }
    }
  }

  private void playerMenu() {
    boolean loop = true;
    while (loop) {
      System.out.println("\n--- PLAYER MANAGEMENT ---");
      System.out.println("1. Add New Player");
      System.out.println("2. View All Players");
      System.out.println("3. Delete Player");
      System.out.println("4. Link User to Player");
      System.out.println("0. Back to Main Dashboard");
      System.out.print("Enter Choice: ");
      int subChoice = input.nextInt();
      input.nextLine();

      switch (subChoice) {
        case 1:
          player.addPlayer();
          break;
        case 2:
          player.viewAllPlayers();
          break;
        case 3:
          player.deletePlayers();
          break;
        case 4:
          System.out.print("Enter Player ID: ");
          int pIdLink = input.nextInt();
          System.out.print("Enter User's User ID (from users table): ");
          int uIdLink2 = input.nextInt();
          input.nextLine();
          player.linkUserToPlayer(pIdLink, uIdLink2);
          break;
        case 0:
          loop = false;
          break;
        default:
          System.out.println("  Invalid Choice!");
      }
    }
  }

  private void auctionMenu() {
    boolean loop = true;
    while (loop) {
      System.out.println("\n-- AUCTION MANAGEMENT ---");
      System.out.println("1. Create New Auction");
      System.out.println("2. Set Team Wallet");
      System.out.println("3. Conduct Player Auction (Bidding)");
      System.out.println("0. Back to Main Dashboard");
      System.out.print("Enter Choice: ");
      int subChoice = input.nextInt();
      input.nextLine();

      switch (subChoice) {
        case 1:
          auction.createAuction();
          break;
        case 2:
          auction.setTeamWallet();
          break;
        case 3:
          auction.conductAuction();
          break;
        case 0:
          loop = false;
          break;
        default:
          System.out.println(" Invalid Choice!");
      }
    }
  }

  private void squadMenu() {
    boolean loop = true;
    while (loop) {
      System.out.println("\n-- SQUAD MANAGEMENT ---");
      System.out.println("1. Build Team Squad");
      System.out.println("2. View Team Squads");
      System.out.println("0. Back to Main Dashboard");
      System.out.print(" Choice: ");
      int subChoice = input.nextInt();
      input.nextLine();

      switch (subChoice) {
        case 1:
          squad.buildSquad();
          break;
        case 2:
          squad.viewSquad();
          break;
        case 0:
          loop = false;
          break;
        default:
          System.out.println(" Invalid Choice!");
      }
    }
  }

  private void matchMenu() {
    boolean loop = true;
    while (loop) {
      System.out.println("\n- MATCH MANAGEMENT ---");
      System.out.println("1. Schedule a New Match");
      System.out.println("2. Assign Teams ");
      System.out.println("3. view all matches ");
      System.out.println("0. Back to Main  Dashboard");
      System.out.print("enter choice: ");
      int subChoice = input.nextInt();
      input.nextLine();

      switch (subChoice) {
        case 1:
          match.scheduleMatch();
          break;
        case 2:
          match.assignTeams();
          break;
        case 3:
          match.viewAllMatches();
          break;
        case 0:
          loop = false;
          break;
        default:
          System.out.println("invalid Choice!");
      }
    }
  }

  private void scoringMenu() {
    boolean loop = true;
    while (loop) {
      System.out.println("\n-- INNINGS & LIVE SCORING ---");
      System.out.println("1. Start Innings");
      System.out.println("2. View Live Scorecard");
      System.out.println("0. Back to Main Dashboard");
      System.out.print("Enter Choice: ");
      int subChoice = input.nextInt();
      input.nextLine();

      switch (subChoice) {
        case 1:
          scoring.startInnings();
          break;
        case 2:
          scoring.displayScorecard();
          break;
        case 0:
          loop = false;
          break;
        default:
          System.out.println(" Invalid Choice!");
      }
    }
  }

  private void staffMenu() {
    boolean loop = true;
    while (loop) {
      System.out.println("\n -- - STAFF & CONTRACT MANAGEMENT ---");
      System.out.println("1. Add New Staff Member");
      System.out.println("2. Assign Staff to Team (Create Contract)");
      System.out.println("3. View Staff & Contracts");
      System.out.println("0. Back to Main Dashboard");
      System.out.print("enter Choice: ");
      int subChoice = input.nextInt();
      input.nextLine();

      switch (subChoice) {
        case 1:
          staff.addStaff();
          break;
        case 2:
          staff.assignStaffToTeam();
          break;
        case 3:
          staff.viewContracts();
          break;
        case 0:
          loop = false;
          break;
        default:
          System.out.println(" Invalid Choice!");
      }
    }
  }
}