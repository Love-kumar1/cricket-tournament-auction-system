package com.cms.entity;

import com.cms.repository.StaffRepository;
import com.cms.repository.TeamRepository;
import com.cms.repository.TeamStaffContractRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Staff {
  private Connection connection;
  private Scanner input;
  Team team;

  private StaffRepository staffRepository;
  private TeamStaffContractRepository teamStaffContractRepository;
  private TeamRepository teamRepository;

  



  public Staff(Connection connection, Scanner input, Team team, StaffRepository staffRepository, TeamStaffContractRepository teamStaffContractRepository, TeamRepository teamRepository) {
    this.connection = connection;
    this.input = input;
    this.team = team;

    this.staffRepository = staffRepository;
    this.teamStaffContractRepository = teamStaffContractRepository;
    this.teamRepository = teamRepository;

    

  }

  public void addStaff() {

    System.out.println("--- ADD STAFF ---");

    System.out.print("Enter Staff Name: ");
    String staffName = input.nextLine();

    System.out.print("Enter Staff Country: ");
    String staffCountry = input.nextLine();

    System.out.print("Enter Experience: ");
    int experience = input.nextInt();
    input.nextLine();

    System.out.print("""
        Enter Staff Role:
          (head_coach, batting_coach, bowling_coach,
          fielding_coach, team_manager, physio,
          analyst, trainer):   
        """);

    String staffRole = input.nextLine();

    String query = staffRepository.insertStaff();

    try {

      PreparedStatement preparedStatement =
          connection.prepareStatement(query);

      preparedStatement.setString(1, staffName);
      preparedStatement.setString(2, staffCountry);
      preparedStatement.setInt(3, experience);
      preparedStatement.setObject(4, staffRole, java.sql.Types.OTHER);

      int rowsAffected =
          preparedStatement.executeUpdate();

      if (rowsAffected > 0) {
        System.out.println("✅ Staff Added Successfully!");
      } else {
        System.out.println("staff no t added");
      }

    } catch (SQLException e) {
        System.out.println("Database error: "+e.getMessage());
      }
  }

  public void assignStaffToTeam() {

    System.out.println("--- ASSIGN STAFF TO TEAM ---");

    viewTeams();

    System.out.print("Enter Team ID: ");
    int teamId = input.nextInt();
    input.nextLine();

    viewStaff();

    System.out.print("Enter Staff ID: ");
    int staffId = input.nextInt();
    input.nextLine();

    System.out.print("Enter Start Date (YYYY-MM-DD): ");
    String durationFrom = input.nextLine();

    System.out.print("Enter End Date (YYYY-MM-DD): ");
    String durationTill = input.nextLine();

    String query = teamStaffContractRepository.insertTeamStaffContract();

    try {

      PreparedStatement preparedStatement =
          connection.prepareStatement(query);

      preparedStatement.setInt(1, teamId);
      preparedStatement.setDate(2,
          java.sql.Date.valueOf(durationFrom));
      preparedStatement.setDate(3,
          java.sql.Date.valueOf(durationTill));
      preparedStatement.setInt(4, staffId);

      int rowsAffected =
          preparedStatement.executeUpdate();

      if (rowsAffected > 0) {
        System.out.println(
            "✅ Staff Assigned Successfully!"
        );
      }

    } catch (SQLException e) {
        System.out.println("Database error: "+e.getMessage());
      }
  }

  public void viewContracts() {

    System.out.println("--- VIEW CONTRACTS ---");

    String query = teamStaffContractRepository.selectTeamStaffContract();

    try {

      PreparedStatement preparedStatement =
          connection.prepareStatement(query);

      ResultSet resultSet =
          preparedStatement.executeQuery();

      System.out.println("+---------+----------------------+----------------------+------------------+------------+------------+");
      System.out.printf("| %-7s | %-20s | %-20s | %-16s | %-10s | %-10s |%n",
          "ID",
          "TEAM",
          "STAFF",
          "ROLE",
          "FROM",
          "TO");
      System.out.println("+---------+----------------------+----------------------+------------------+------------+------------+");

      while (resultSet.next()) {

        System.out.printf(
            "| %-7d | %-20s | %-20s | %-16s | %-10s | %-10s |%n",
            resultSet.getInt("contract_id"),
            resultSet.getString("team_name"),
            resultSet.getString("staff_name"),
            resultSet.getString("role"),
            resultSet.getDate("duration_from"),
            resultSet.getDate("duration_till")
        );
      }

      System.out.println("+---------+----------------------+----------------------+------------------+------------+------------+");

    } catch (SQLException e) {
        System.out.println("Database error: "+e.getMessage());
      }
  }

  private void viewStaff() {

    System.out.println("--- VIEW STAFF ---\n");

    String query = staffRepository.selectAllFromStaff();

    try {

      PreparedStatement preparedStatement =
          connection.prepareStatement(query);

      ResultSet resultSet =
          preparedStatement.executeQuery();

      System.out.println("+----------+----------------------+--------------+------------+------------------+");
      System.out.printf("| %-8s | %-20s | %-12s | %-10s | %-16s |%n",
          "ID",
          "NAME",
          "COUNTRY",
          "EXP",
          "ROLE");
      System.out.println("+----------+----------------------+--------------+------------+------------------+");

      while (resultSet.next()) {

        System.out.printf(
            "| %-8d | %-20s | %-12s | %-10d | %-16s |%n",
            resultSet.getInt("staff_id"),
            resultSet.getString("staff_name"),
            resultSet.getString("staff_country"),
            resultSet.getInt("experience"),
            resultSet.getString("staff_role")
        );
      }

      System.out.println("+----------+----------------------+--------------+------------+------------------+");

    } catch (SQLException e) {
        System.out.println("Database error: "+e.getMessage());
      }
  }

  private void viewTeams() {

    String query = teamRepository.selectOrderedFromTeams();

    try {

      PreparedStatement preparedStatement =
          connection.prepareStatement(query);

      ResultSet resultSet =
          preparedStatement.executeQuery();

      System.out.println("+---------+----------------------+----------------------+----------------------+");
      System.out.printf("| %-7s | %-20s | %-20s | %-20s |%n",
          "ID",
          "TEAM NAME",
          "CITY",
          "OWNER");
      System.out.println("+---------+----------------------+----------------------+----------------------+");

      while (resultSet.next()) {

        System.out.printf(
            "| %-7d | %-20s | %-20s | %-20s |%n",
            resultSet.getInt("team_id"),
            resultSet.getString("team_name"),
            resultSet.getString("team_city"),
            resultSet.getString("team_owner")
        );
      }

      System.out.println("+---------+----------------------+----------------------+----------------------+");

    } catch (SQLException e) {
        System.out.println("Database error: "+e.getMessage());
      }
  }
}
