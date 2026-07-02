package com.cms.entity;

import com.cms.repository.TournamentRepository;
import com.cms.utils.InputValidator;

import java.sql.*;
import java.util.Scanner;

public class Tournament {
  private Connection connection;
  private Scanner input;
  private Team team;

  private TournamentRepository tournamentRepository;


  public Tournament(Connection connection, Scanner input, TournamentRepository tournamentRepository, Team team) {
    this.connection = connection;
    this.input = input;
    this.team = team;

    this.tournamentRepository = tournamentRepository;
    
  }

  public void addTournament() {
    System.out.println("--- ADD TOURNAMENTS ---");
    String query = tournamentRepository.insertTournaments();
    System.out.print("Enter Tournament Name    : ");
    String tName = input.nextLine();

    if (!InputValidator.isValidTournamentName(tName)) {
      return;
    }

    System.out.print("Enter Tournament Year    : ");
    int tYear = input.nextInt();
    input.nextLine();

    if (!InputValidator.isValidTournamentName(tName)) {
      return;
    }

    System.out.print("Enter Tournament Country : ");
    String tCountry = input.nextLine();

    if (!InputValidator.isValidCountry(tCountry)) {
      return;
    }

    System.out.print("Tournament Status(upcoming, ongoing, completed): ");
    String tStatus = input.nextLine().toLowerCase();

    if (!InputValidator.isValidTournamentStatus(tStatus)) {
      return;
    }


    try {
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setString(1, tName);
      preparedStatement.setInt(2, tYear);
      preparedStatement.setString(3, tCountry);
      preparedStatement.setObject(4, tStatus, java.sql.Types.OTHER);

      int rowsAffected = preparedStatement.executeUpdate();
      if (rowsAffected > 0) {
        System.out.println("Creating Tournament... ");
        System.out.println(" ✅ Tournament " + tName + " added successfully.! [ID: " + getTournamentId() + "]");
      } else {
        System.out.println("❌ Tournament failed to add..!");
      }
    } catch (SQLException e) {
        System.out.println("Database error: "+e.getMessage());
      }

  }

  public void viewAllTournaments() {
    System.out.println("--- VIEW ALL TOURNAMENTS ---");
    String query = tournamentRepository.viewAllTournaments();

    try {
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      ResultSet resultSet = preparedStatement.executeQuery();

      Statement statement = connection.createStatement();
      ResultSet resultSet1 = statement.executeQuery("SELECT count(*) FROM tournament;");
      int count = 0;
      if (resultSet1.next()) {
        count = resultSet1.getInt("count");
      }

      System.out.println("AllTournaments: ");
      System.out.println("| id |    Tournament name    |  Year  |   Country   |   Status  |       Winner       |");
      System.out.println("+----+-----------------------+--------+-------------+-----------+--------------------+");

      while (resultSet.next()) {
        int tId = resultSet.getInt("tournament_id");
        String tName = resultSet.getString("tournament_name");
        int tYear = resultSet.getInt("tournament_year");
        String tCountry = resultSet.getString("tournament_country");
        String tStatus = resultSet.getString("tournament_status");
        String tWinner = resultSet.getString("winner");

        System.out.printf("| %-2s | %-21s | %-6s | %-11s | %-9s | %-18s |\n", tId, tName, tYear, tCountry, tStatus, tWinner);
      }
      System.out.println("+----+-----------------------+--------+-------------+-----------+--------------------+");

      System.out.println("Total Records: " + count);
    } catch (SQLException e) {
        System.out.println("Database error: "+e.getMessage());
      }
  }

  public void updateTournament() {
    System.out.println("--- UPDATE TOURNAMENT ---");
    System.out.print("Enter Tournament ID to Update: ");
    int uId = input.nextInt();
    input.nextLine();

    System.out.println();
    if (recordExists(uId)) {

      System.out.println("What to Update?");
      System.out.println("  1. Status");
      System.out.println("  2. Winner Team");

      System.out.print("Enter Choice: ");
      int choice = input.nextInt();
      input.nextLine();


      System.out.println();

      if (choice == 2) {
        String query = tournamentRepository.updateTournament();
        team.viewAllTeams();
        System.out.print("Select Winner Team ID: ");
        int tWId = input.nextInt();
        input.nextLine();

        try {
          PreparedStatement preparedStatement = connection.prepareStatement(query);
          preparedStatement.setInt(1, tWId);
          preparedStatement.setInt(2, uId);
          int rowsAffected = preparedStatement.executeUpdate();
          if (rowsAffected > 0) {
            System.out.println("✅ Tournament winner updated to " + tWId);
          } else {
            System.out.println();
          }
        } catch (SQLException e) {
        System.out.println("Database error: "+e.getMessage());
      }

      }
      else if (choice == 1) {
        String query = tournamentRepository.updateTournamentStatus();
        System.out.println("Enter Status that you want to set ");
        System.out.print("    (upcoming, ongoing, completed): ");
        String status = input.nextLine().toLowerCase();

        try {
          PreparedStatement preparedStatement = connection.prepareStatement(query);
          preparedStatement.setObject(1, status, java.sql.Types.OTHER);
          preparedStatement.setInt(2, uId);

          int rowsAffected = preparedStatement.executeUpdate();
          if (rowsAffected > 0) {
            System.out.println("✅ Tournament Status updated to " + status + "...");
          } else {
            System.out.println("❌ Update was not successful...!");
          }
        } catch (SQLException e) {
        System.out.println("Database error: "+e.getMessage());
      }
      } else {
        System.out.println(" ⚠\uFE0F Please, enter valid choice...!");
      }

    } else {
      System.out.println("Record for given id does not exist..!");
    }

  }

  public void deleteTournament() {
    System.out.println("--- DELETE TOURNAMENTS ---");
    String query = tournamentRepository.deleteTournaments();
    System.out.print("Enter Tournament ID to Delete: ");
    int tId = input.nextInt();
    input.nextLine();
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setInt(1, tId);
      System.out.println();
      System.out.println("⚠\uFE0F This will delete all related auction, match, and squad data.");
      System.out.print("Are you sure to porceed:(yes/no): ");
      String choice = input.nextLine().toLowerCase();
      if (choice.equals("yes")) {
        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected > 0) {
          System.out.println();
          System.out.println("✅ Tournament [ID: " + tId + "] Deleted successfully..! ");
        } else {
          System.out.println("❌ Tournament failed to delete");
        }
      } else {
        System.out.println();
        System.out.println("ok this record will not be deleted");
      }

    } catch (SQLException e) {
        System.out.println("Database error: "+e.getMessage());
      }


  }

  int getTournamentId() {
    String query = tournamentRepository.getLastTournamentId();
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      ResultSet resultSet = preparedStatement.executeQuery();

      if (resultSet.next()) {
        int id = resultSet.getInt("tournament_id");
//        System.out.println(id);
        return id;
      } else {
        return 0;
      }
    } catch (SQLException e) {
        System.out.println("Database error: "+e.getMessage());
      }
    return 0;

  }

  boolean recordExists(int id) {
    String query = tournamentRepository.allTournaments();
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setInt(1, id);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        return true;
      } else {
        return false;
      }
    } catch (SQLException e) {
        System.out.println("Database error: "+e.getMessage());
      }
    return false;
  }
}



