package com.cms.entity;

import com.cms.repository.MatchRepository;
import com.cms.repository.MatchTeamRepository;
import com.cms.repository.TeamAuctionRepository;
import com.cms.repository.TournamentRepository;
import com.cms.utils.InputValidator;

import java.sql.*;
import java.util.Date;
import java.util.Scanner;

public class Match {
  private Connection connection;
  private Scanner input;

  private MatchRepository matchRepository;
  private MatchTeamRepository matchTeamRepository;
  private TournamentRepository tournamentRepository;
  private TeamAuctionRepository teamAuctionRepository;

  


  public Match(
      Connection connection,
      Scanner scanner,
      MatchRepository matchRepository,
      MatchTeamRepository matchTeamRepository,
      TournamentRepository tournamentRepository,
      TeamAuctionRepository teamAuctionRepository
      
  ) {
    this.connection = connection;
    this.input = scanner;

    this.matchRepository = matchRepository;
    this.matchTeamRepository = matchTeamRepository;
    this.tournamentRepository = tournamentRepository;
    this.teamAuctionRepository = teamAuctionRepository;

    


  }

  public void scheduleMatch() {
    String query = matchRepository.insertMatch();

    System.out.println("Available Tournaments: ");
    activeTournaments();

    System.out.print("Enter tournament ID: ");
    int tId = input.nextInt();
    input.nextLine();
    if (!InputValidator.isValidId(tId)) {
      return;
    }
    System.out.println();

    System.out.println("--- SCHEDULE MATCH --- \n");

    System.out.print("Enter Match Venue: ");
    String venue = input.nextLine();
    if (!InputValidator.isValidVenue(venue)) {
      return;
    }

    System.out.print("Enter Date (YYYY-MM-DD): ");
    String date = input.nextLine().trim();
    if (!InputValidator.isValidDate(date)) {
      return;
    }

    System.out.print("Enter Time (HH:MM): ");
    String time = input.nextLine().trim();
    if (!InputValidator.isValidTime(time)) {
      return;
    }

    System.out.println("Enter Match Type ");
    System.out.print("   Options (t20, odi, test): ");
    String type = input.nextLine().toLowerCase().trim();
    if (!InputValidator.isValidMatchType(type)) {
      return;
    }

    int overs = 0;
    switch (type) {
      case "t20":
        overs = 20;
        break;
      case "odi":
        overs = 50;
        break;
      case "test":
        overs = 450;
        break;
      default:
        System.out.println("❌ Invalid choice entered!");
        return;
    }

    try {
      java.sql.Date sqlDate = java.sql.Date.valueOf(date);
      java.sql.Time sqlTime = java.sql.Time.valueOf(time + ":00");

      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setString(1, venue);

      preparedStatement.setDate(2, sqlDate);
      preparedStatement.setTime(3, sqlTime);

      preparedStatement.setObject(4, type, java.sql.Types.OTHER);
      preparedStatement.setInt(5, overs);
      preparedStatement.setObject(6, "upcoming", java.sql.Types.OTHER);

      preparedStatement.setInt(7, tId);

      int rowsAffected = preparedStatement.executeUpdate();
      if (rowsAffected > 0) {
        System.out.println("✅ Match Scheduled successfully!");
      } else {
        System.out.println("❌ Match Not Scheduled!");
      }

    } catch (IllegalArgumentException e) {
      System.out.println("❌ Error: date time format is invalid!");
    } catch (SQLException e) {
      System.out.println("❌ Database Error: " + e.getMessage());
    }
  }

  public void assignTeams() {
    String query = matchTeamRepository.insertMatchTeam();

    System.out.println("Available Tournaments: ");
    activeTournaments();

    System.out.print("Enter tournament ID: ");
    int tId = input.nextInt();
    input.nextLine();
    System.out.println();

    System.out.println("--- ASSIGN TEAMS TO MATCH --- \n");
    System.out.println("Available Teams In Tournament [ID: " + tId + "]: \n");
    activeTeams(tId);

    System.out.print("Enter 1st Team ID: ");
    int team1Id = input.nextInt();
    System.out.print("Enter 2nd Team ID: ");
    int team2Id = input.nextInt();
    System.out.println();

    System.out.println("Available Matches:  ");

    activeMatches(tId);

    System.out.print("Enter Match ID to assign: ");
    int matchId = input.nextInt();
    input.nextLine();
    System.out.println();

    try {
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setInt(1, matchId);
      preparedStatement.setInt(2, team1Id);
      preparedStatement.executeUpdate();

      PreparedStatement preparedStatement2 = connection.prepareStatement(query);
      preparedStatement2.setInt(1, matchId);
      preparedStatement2.setInt(2, team2Id);
      int rowsAffected = preparedStatement2.executeUpdate();

      if (rowsAffected > 0) {
        System.out.println("Team Assigned successfully!");
      } else {
        System.out.println("Team Not Assigned!");
      }

    } catch (SQLException e) {
        System.out.println("Database error: "+e.getMessage());
      }

  }

  public void viewAllMatches() {

    activeTournaments();

    System.out.print("Enter Tournament ID: ");
    int tournamentId = input.nextInt();

    String sql = tournamentRepository.allTournaments();
    String tName = null;
    int tYear = 0;
    try {
      PreparedStatement pStmt = connection.prepareStatement(sql);
      pStmt.setInt(1, tournamentId);
      ResultSet rs = pStmt.executeQuery();
      if (rs.next()) {
        tName = rs.getString("tournament_name");
        tYear = rs.getInt("tournament_year");
      }
    }catch (SQLException e) {
        System.out.println("Database error: "+e.getMessage());
      }
    input.nextLine();
    System.out.println();

    System.out.println("--- Match Schedule : "+tName+"-"+tYear+" ---\n");

    String query = matchTeamRepository.showAllMatches();

    try {
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setInt(1, tournamentId);

      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        System.out.println("+-----+----------------------+--------------+-----------+-------------+--------------------+");
        System.out.println("|  #  |     Match Venue      |     Date     |    Type   |    Status   |        Teams       |");
        System.out.println("+-----+----------------------+--------------+-----------+-------------+--------------------+");

        while (resultSet.next()) {
          int mId = resultSet.getInt("match_id");
          String mVenue = resultSet.getString("match_venue");
          String mDate = resultSet.getString("match_date");
          String mType = resultSet.getString("match_type");
          String mStatus = resultSet.getString("match_status");
          String mTeams = resultSet.getString("teams");

          System.out.printf("| %-3s | %-20s | %-12s | %-9s | %-11s | %-18s |\n", mId, mVenue, mDate, mType, mStatus, mTeams);

        }
        System.out.println("+-----+----------------------+--------------+-----------+-------------+--------------------+");
      } else {
        System.out.println("NO RECORDS FOUND...");
      }

    } catch (SQLException e) {
        System.out.println("Database error: "+e.getMessage());
      }

  }

  private void activeTournaments() {
    String query = tournamentRepository.upcoming_ongoingTournaments();
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

  private void activeTeams(int tournament_id) {
    String query = teamAuctionRepository.activeAuctionTeams();
    int serial = 0;
    try {

      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setInt(1, tournament_id);

      ResultSet rs = preparedStatement.executeQuery();
      while (rs.next()) {
        serial++;
        String tName = rs.getString("team_name");
        int tId = rs.getInt("team_id");

        System.out.println(serial + ". " + tName + "[ID: " + tId + "]");
      }
    } catch (SQLException e) {
        System.out.println("Database error: "+e.getMessage());
      }
  }

  private void activeMatches(int tournament_id) {
    String query = matchTeamRepository.upcomingMatches();

    int serial = 0;
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setInt(1, tournament_id);
      ResultSet rs = preparedStatement.executeQuery();
      if (rs.next()) {
        while (rs.next()) {
          int mId = rs.getInt("match_id");
          String mVenue = rs.getString("match_venue");
          Date mDate = rs.getDate("match_date");
          Time mTime = rs.getTime("match_time");
          String mType = rs.getString("match_type");

          System.out.println(serial + ". [ID: " + mId + "] " + mType + " " + mVenue + " " + mDate + " " + mTime);

        }
      } else {
        System.out.println("No records found!");
      }
      while (rs.next()) {
      }
    } catch (SQLException e) {
        System.out.println("Database error: "+e.getMessage());
      }

  }

}
