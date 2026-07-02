  package com.cms.entity;

  import com.cms.repository.TeamRepository;
  import com.cms.utils.InputValidator;

  import java.sql.*;
  import java.util.Scanner;

  public class Team {
    private Connection connection;
    private Scanner input;

    private TeamRepository teamRepository;
    

    public Team(Connection connection, Scanner input, TeamRepository teamRepository) {
      this.connection = connection;
      this.input = input;

      this.teamRepository = teamRepository;
      
    }

    public void addTeam() {
      System.out.println("--- ADD TEAMS ---");
      String query = teamRepository.insertTeam();
      System.out.print("Enter Team Name : ");
      String tName = input.nextLine();
      if (!InputValidator.isValidTeamName(tName)) {
        return;
      }

      System.out.print("Enter Team City : ");
      String tCity = input.nextLine();

      if (!InputValidator.isValidCity(tCity)) {
        return;
      }

      System.out.print("Enter Team Owner: ");
      String tOwner = input.nextLine();

      if (!InputValidator.isValidOwner(tOwner)) {
        return;
      }
      try {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, tName);
        preparedStatement.setString(2, tCity);
        preparedStatement.setString(3, tOwner);

        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected > 0) {
          System.out.println("Creating Team... ");
          System.out.println(" ✅ Team " + tName + " added successfully.! [ID: " + getTeamId() + "]");
        } else {
          System.out.println("❌ Tournament failed to add..!");
        }
      } catch (SQLException e) {
          System.out.println("Database error: "+e.getMessage());
        }

    }

    public void viewAllTeams() {
      String query = teamRepository.selectAllTeams();
      try {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        Statement statement = connection.createStatement();
        ResultSet resultSet1 = statement.executeQuery("SELECT count(*) FROM team;");
        int count = 0;
        if (resultSet1.next()) {
          count = resultSet1.getInt("count");
        }

        System.out.println("--- VIEW ALL TEAMS ---");
        System.out.println(" AllTeams: ");
        System.out.println("| id |     Team name     |        City       |      Owner       |");
        System.out.println("+----+-------------------+-------------------+------------------+");

        while (resultSet.next()) {
          int tId = resultSet.getInt("team_id");
          String tName = resultSet.getString("team_name");
          String tCity = resultSet.getString("team_city");
          String tOwner = resultSet.getString("team_owner");

          System.out.printf("| %-2s | %-17s | %-17s | %-16s |\n", tId, tName, tCity, tOwner);

        }

        System.out.println("+----+-------------------+-------------------+------------------+");

        System.out.println("Total Records: " + count);
      } catch (SQLException e) {
          System.out.println("Database error: "+e.getMessage());
        }
    }

    public void updateTeam() {
      System.out.println("--- UPDATE TEAMS ---");
      System.out.print("Enter Team ID to Update: ");
      int uId = input.nextInt();
      input.nextLine();

      System.out.println();
      if (recordExists(uId)) {

        System.out.println("What to Update?");
        System.out.println("  1. Team Owner");
        System.out.println("  2. Team city");

        System.out.print("Enter Choice: ");
        int choice = input.nextInt();
        input.nextLine();


        System.out.println();

        if (choice == 2) {
          String query = teamRepository.updateTeamCity();
          System.out.print("Enter the team city: ");
          String tCity = input.nextLine();

          try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, tCity);
            preparedStatement.setInt(2, uId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
              System.out.println("✅ Team city updated to " + tCity);
            } else {
              System.out.println("Update failed...!");
            }
          } catch (SQLException e) {
          System.out.println("Database error: "+e.getMessage());
        }

        }
        else if (choice == 1) {
          String query = teamRepository.updateTeamOwner();
          System.out.println("Enter Owner that you want to set ");
          String tOwner = input.nextLine();

          try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, tOwner);
            preparedStatement.setInt(2, uId);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
              System.out.println("✅ Team Owner updated to " + tOwner + "...");
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

    public void deleteTeams() {
      System.out.println("--- DELETE TEAMS ---");
      String query = teamRepository.deleteTeam();
      System.out.print("Enter Team ID to Delete: ");
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
            System.out.println("✅ Team [ID: " + tId + "] Deleted successfully..! ");
          } else {
            System.out.println("❌ Team failed to delete");
          }
        }else {
          System.out.println();
          System.out.println("ok, this record will not be deleted");
        }

      } catch (SQLException e) {
          System.out.println("Database error: "+e.getMessage());
        }


    }

    int getTeamId() {
      String query = teamRepository.selectLastTeam();
      try {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
          int id = resultSet.getInt("team_id");
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
      String query = teamRepository.selectAllFromTeams();
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

    public void linkManagerToTeam(int teamId, int userId) {
      String query = teamRepository.linkManagerToTeam();
      try {
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, userId);
        ps.setInt(2, teamId);
        int rows = ps.executeUpdate();
        if (rows > 0) {
          System.out.println("✅ Manager linked to Team successfully!");
        } else {
          System.out.println("❌ Linking failed.");
        }
      } catch (SQLException e) {
        System.out.println("Database error: " + e.getMessage());
      }
    }

  }
