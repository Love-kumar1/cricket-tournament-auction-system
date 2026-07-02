package com.cms.entity;

import com.cms.repository.PlayerRepository;
import com.cms.utils.InputValidator;

import java.sql.*;
import java.util.Scanner;

public class Player {
  private Connection connection;
  private Scanner input;

  private PlayerRepository playerRepository;

  


  public Player(Connection connection, Scanner input, PlayerRepository playerRepository) {
    this.connection = connection;
    this.input = input;

    this.playerRepository = playerRepository;
    

  }

  public void addPlayer() {
    System.out.println("--- ADD PLAYERS ---");
    String query = playerRepository.insertPlayer();

    System.out.print("Enter Player Name : ");
    String pName = input.nextLine();
    if (!InputValidator.isValidPlayerName(pName)) {
      return;
    }

    System.out.print("Enter Player Country: ");
    String pCountry = input.nextLine();
    if (!InputValidator.isValidCountry(pCountry)) {
      return;
    }

    System.out.print("Enter Player Age: ");
    int pAge = input.nextInt();
    input.nextLine();
    if (!InputValidator.isValidPlayerAge(pAge)) {
      return;
    }

    System.out.println("Enter Player Role ");
    System.out.print("    options(batsman / bowler / all rounder / wicket keeper): ");
    String pRole = input.nextLine().toLowerCase();
    if (!InputValidator.isValidPlayerRole(pRole)) {
      return;
    }

    try {
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setString(1, pName);
      preparedStatement.setString(2, pCountry);
      preparedStatement.setInt(3, pAge);
      preparedStatement.setObject(4, pRole, java.sql.Types.OTHER);

      int rowsAffected = preparedStatement.executeUpdate();
      if (rowsAffected > 0) {
        System.out.println("Creating Player... ");
        System.out.println(" ✅ Player " + pName + " added successfully.! [ID: " + getPlayerId() + "]");
      } else {
        System.out.println("❌ Player failed to add..!");
      }
    } catch (SQLException e) {
        System.out.println("Database error: "+e.getMessage());
      }

  }

  public void viewAllPlayers() {
    System.out.println("--- VIEW ALL PLAYERS ---");
    String query = playerRepository.allPlayer();
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      ResultSet resultSet = preparedStatement.executeQuery();

      Statement statement = connection.createStatement();
      ResultSet resultSet1 = statement.executeQuery("SELECT count(*) FROM player;");
      int count = 0;
      if (resultSet1.next()) {
        count = resultSet1.getInt("count");
      }

      System.out.println(" ---AllPlayers: ");
      System.out.println("+----+---------------------+-------------------+-------+---------------+");
      System.out.println("| id |     Player name     |      Country      |  Age  |      Role     |");
      System.out.println("+----+---------------------+-------------------+-------+---------------+");

      while (resultSet.next()) {
        int pId = resultSet.getInt("player_id");
        String pName = resultSet.getString("player_name");
        String pCountry = resultSet.getString("player_country");
        int pAge = resultSet.getInt("player_age");
        String pRole = resultSet.getString("player_role");

        System.out.printf("| %-2s | %-19s | %-17s | %-5s | %-13s |\n", pId, pName, pCountry, pAge, pRole);
      }
      System.out.println("+----+---------------------+-------------------+-------+---------------+");


      System.out.println("Total Records: " + count);
    } catch (SQLException e) {
        System.out.println("Database error: "+e.getMessage());
      }
  }

  public void deletePlayers() {
    System.out.println("--- DELETE PLAYER ---");
    String query = playerRepository.removePlayer();
    System.out.print("Enter Team ID to Delete: ");
    int pId = input.nextInt();
    input.nextLine();
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setInt(1, pId);
      System.out.println();
      System.out.println("⚠\uFE0F This will delete all related auction, match, and squad data.");
      System.out.print("Are you sure to porceed:(yes/no): ");
      String choice = input.nextLine().toLowerCase();
      if (choice.equals("yes")) {
        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected > 0) {
          System.out.println();
          System.out.println("✅ Team [ID: " + pId + "] Deleted successfully..! ");
        } else {
          System.out.println("❌ Team failed to delete");
        }
      } else {
        System.out.println();
        System.out.println("ok, this record will not be deleted");
      }

    } catch (SQLException e) {
        System.out.println("Database error: "+e.getMessage());
      }


  }

  int getPlayerId() {
    String query = playerRepository.getLastPlayerId();
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      ResultSet resultSet = preparedStatement.executeQuery();

      if (resultSet.next()) {
        int id = resultSet.getInt("player_id");
        return id;
      } else {
        return 0;
      }
    } catch (SQLException e) {
        System.out.println("Database error: "+e.getMessage());
      }
    return 0;

  }

  public void linkUserToPlayer(int playerId, int userId) {
    String query = playerRepository.linkUserToPlayer();
    try {
      PreparedStatement ps = connection.prepareStatement(query);
      ps.setInt(1, userId);
      ps.setInt(2, playerId);
      int rows = ps.executeUpdate();
      if (rows > 0) {
        System.out.println("✅ User linked to Player successfully!");
      } else {
        System.out.println("❌ Linking failed.");
      }
    } catch (SQLException e) {
      System.out.println("Database error: " + e.getMessage());
    }
  }

}
