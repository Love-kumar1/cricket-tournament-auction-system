package com.cms.entity;

import com.cms.repository.PlayerTeamAuctionRepository;
import com.cms.repository.TeamAuctionRepository;
import com.cms.repository.TeamSquadRepository;
import com.cms.repository.TournamentRepository;

import java.sql.*;
import java.util.Scanner;

public class Squad {
  private Connection connection;
  private Scanner input;

  private TeamSquadRepository teamSquadRepository;
  private TournamentRepository tournamentRepository;
  private TeamAuctionRepository teamAuctionRepository;
  private PlayerTeamAuctionRepository playerTeamAuctionRepository;

  


  public Squad(Scanner input, Connection connection, TeamSquadRepository teamSquadRepository, TournamentRepository tournamentRepository, TeamAuctionRepository teamAuctionRepository) {
    this.input = input;
    this.connection = connection;

    this.teamSquadRepository = teamSquadRepository;
    this.tournamentRepository = tournamentRepository;
    this.teamAuctionRepository = teamAuctionRepository;

    

  }


  public void buildSquad() {

    System.out.println("--- BUILD SQUAD ---");
    System.out.println();

    String query = teamSquadRepository.insertTeamSquad();

    System.out.println("--- Available Tournaments for Squad Creation ---");
    activeTournaments();

    System.out.println();
    System.out.print("Enter tournament ID to proceed: ");
    int tourId = input.nextInt();
    input.nextLine();
    System.out.println();

    System.out.println("Loading Participating teams in Tournament [ID: " + tourId + "] ...");
    System.out.println();
    activeTeams(tourId);
    System.out.println();

    System.out.print("Enter Team ID to manage: ");
    int teamId = input.nextInt();
    input.nextLine();
    System.out.println();

    System.out.println("Eligible Players (auctioned by team [ID: " + teamId + "] )");
    eligiblePlayers(teamId, tourId);
    System.out.println();

    System.out.print("Enter player Id to ADD to squad: ");
    int playerId = input.nextInt();
    input.nextLine();

    System.out.println("Enter team role ");
    System.out.print("  Options (captain/ vice_captain/ player): ");
    String role = input.nextLine();

    if (isPlayerAlreadyInSquad(playerId, tourId)) {
      System.out.println("❌ Error: this player already exists in this tournament's squad!");
      return;
    }

    if (hasRoleAlready(teamId, tourId, role)) {
      System.out.println("❌ Error: this team has " + role + "already !");
      return;
    }

    try {
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setInt(1, teamId);
      preparedStatement.setInt(2, playerId);
      preparedStatement.setInt(3, tourId);
      preparedStatement.setObject(4, role, Types.OTHER);
      int rowsAffected = preparedStatement.executeUpdate();
      if (rowsAffected > 0) {
        System.out.println(" Successfully added player to tournament");
      } else {
        System.out.println("Failed to add player to tournament");
      }
    } catch (SQLException e) {
        System.out.println("Database error: "+e.getMessage());
      }


  }

  public void viewSquad() {
    System.out.print("Enter Team ID: ");
    int teamId = input.nextInt();
    input.nextLine();
    System.out.println();

    System.out.print("Enter Tournament ID: ");
    int tournamentId = input.nextInt();
    input.nextLine();
    System.out.println();

    System.out.println("--- VIEW SQUAD ---");
    String query = """
          SELECT ts.squad_id, p.player_name, p.player_country, p.player_role, ts.team_role
          FROM team_squad ts
          JOIN player p ON ts.player_id = p.player_id
          WHERE ts.team_id = ? AND ts.tournament_id = ?
          ORDER BY ts.team_role;
        """;
    System.out.println();

    try {
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setInt(1, teamId);
      preparedStatement.setInt(2, tournamentId);

      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        System.out.println("+-----+---------------------+-------------------+---------------+");
        System.out.println("|  #  |     Player name     |      Country      |      Role     |");
        System.out.println("+-----+---------------------+-------------------+---------------+");

        while (resultSet.next()) {
          int squadId = resultSet.getInt("squad_id");
          String playerName = resultSet.getString("player_name");
          String playerCountry = resultSet.getString("player_country");
          String playerRole = resultSet.getString("player_role");
          String teamRole = resultSet.getString("team_role");

          System.out.printf("| %-3s | %-19s | %-17s | %-13s |\n", squadId, playerName, playerCountry, playerRole);


        }
        System.out.println("+-----+---------------------+-------------------+---------------+");
      } else {
        System.out.println("NO RECORDS FOUND...");
      }


    } catch (SQLException e) {
        System.out.println("Database error: "+e.getMessage());
      }


  }

  private void activeTournaments() {
    String query = tournamentRepository.upcomingTournaments();
    int serial = 0;
    try {

      Statement stmt = connection.createStatement();
      ResultSet rs = stmt.executeQuery(query);
      while (rs.next()) {
        serial++;
        String tName = rs.getString("tournament_name");
        int tYear = rs.getInt("tournament_year");
        int tId = rs.getInt("tournament_id");

        System.out.println(serial + ". " + tName + " " + tYear + " [ID: " + tId + "]");
      }
    } catch (SQLException e) {
        System.out.println("Database error: "+e.getMessage());
      }
  }

  private void activeTeams(int tourId) {
    String query = teamAuctionRepository.activeAuctionTeams();
    int serial = 0;
    try {

      PreparedStatement pStmt = connection.prepareStatement(query);
      pStmt.setInt(1, tourId);
      ResultSet rs = pStmt.executeQuery();
      while (rs.next()) {
        serial++;
        String tName = rs.getString("team_name");
        int tId = rs.getInt("team_id");

        System.out.println(serial + ". " + tName + " " + " [ID: " + tId + "]");
      }
    } catch (SQLException e) {
        System.out.println("Database error: "+e.getMessage());
      }
  }

  private void eligiblePlayers(int teaId, int tourId) {
    String query = playerTeamAuctionRepository.eligiblePlayerSelection();
    int serial = 0;
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setInt(1, teaId);
      preparedStatement.setInt(2, tourId);
      ResultSet rs = preparedStatement.executeQuery();

      while (rs.next()) {
        serial++;
        String pName = rs.getString("player_name");
        String pRole = rs.getString("player_role");
        int pId = rs.getInt("player_id");

        System.out.println(serial + ". " + pName + " - " + pRole + " [ID: " + pId + "]");
      }
    } catch (SQLException e) {
        System.out.println("Database error: "+e.getMessage());
      }
  }

  private boolean isPlayerAlreadyInSquad(int playerId, int tournamentId) {
    String sql = teamSquadRepository.selectAllFromTeamSquad();
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setInt(1, playerId);
      stmt.setInt(2, tournamentId);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) return rs.getInt(1) > 0;
      }
    } catch (SQLException e) {
        System.out.println("Database error: "+e.getMessage());
      }
    return false;
  }

  private boolean hasRoleAlready(int teamId, int tournamentId, String role) {
    if (role.equals("player")) return false;

    String sql = teamSquadRepository.hasAlreadyRole();
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setInt(1, teamId);
      stmt.setInt(2, tournamentId);
      stmt.setString(3, role);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) return rs.getInt(1) > 0;
      }
    } catch (SQLException e) {
        System.out.println("Database error: "+e.getMessage());
      }
    return false;
  }



}
