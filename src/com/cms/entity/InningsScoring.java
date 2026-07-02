package com.cms.entity;

import com.cms.repository.Innings_ScoringRepository;
import com.cms.repository.MatchRepository;
import com.cms.repository.MatchTeamRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class InningsScoring {
  private Connection connection;
  private Scanner input;
  private Match match;

  private Innings_ScoringRepository inningsScoringRepository;
  private MatchRepository matchRepository;
  private MatchTeamRepository matchTeamRepository;




  public InningsScoring(Connection connection, Scanner input, Match match, Innings_ScoringRepository inningsScoringRepository, MatchRepository matchRepository, MatchTeamRepository matchTeamRepository) {
    this.connection = connection;
    this.input = input;
    this.match = match;

    this.inningsScoringRepository = inningsScoringRepository;
    this.matchRepository = matchRepository;
    this.matchTeamRepository = matchTeamRepository;

    

  }

  public void startInnings() {
    System.out.println("--- START INNINGS ---");

    System.out.print("Enter Match ID: ");
    int matchId = input.nextInt();
    input.nextLine();

    if (!matchExists(matchId)) {
      System.out.println(" Match does not exist.");
      return;
    }

    System.out.print("Enter Innings Number (1/2): ");
    int inningsNumber = input.nextInt();
    input.nextLine();

    if (inningsExists(matchId, inningsNumber)) {
      System.out.println(" Innings already exists for this match.");
      return;
    }

    System.out.println();
    System.out.println("Teams in Match:");
    viewMatchTeams(matchId);

    System.out.print("Enter Batting Team ID: ");
    int battingTeamId = input.nextInt();

    System.out.print("Enter Bowling Team ID: ");
    int bowlingTeamId = input.nextInt();
    input.nextLine();

    if (battingTeamId == bowlingTeamId) {
      System.out.println(" Batting and Bowling team cannot be the same.");
      return;
    }

    String query = inningsScoringRepository.insertInning();

    try {
      PreparedStatement preparedStatement =
          connection.prepareStatement(query);

      preparedStatement.setInt(1, inningsNumber);
      preparedStatement.setInt(2, matchId);
      preparedStatement.setInt(3, battingTeamId);
      preparedStatement.setInt(4, bowlingTeamId);

      int rowsAffected = preparedStatement.executeUpdate();

      if (rowsAffected > 0) {
        System.out.println();
        System.out.println("Creating Innings...");
        System.out.println(" Innings " + inningsNumber +
            " started successfully! [ID: " + getInningsId() + "]");
      } else {
        System.out.println(" Failed to start innings.");
      }

    } catch (SQLException e) {
        System.out.println("Database error: "+e.getMessage());
      }
  }

  public void displayScorecard() {

    System.out.println("--- DISPLAY SCORECARD ---");

    System.out.print("Enter Innings ID: ");
    int inningsId = input.nextInt();
    input.nextLine();

    if (!inningsExists(inningsId)) {
      System.out.println("❌ Innings does not exist.");
      return;
    }

    String query = inningsScoringRepository.scorecardQuery();

    try {

      PreparedStatement preparedStatement =
          connection.prepareStatement(query);

      preparedStatement.setInt(1, inningsId);

      ResultSet resultSet =
          preparedStatement.executeQuery();

      if (resultSet.next()) {

        System.out.println();
        System.out.println("----------------------------------");
        System.out.println("          INNINGS SCORECARD");
        System.out.println("----------------------------------");

        System.out.println(
            "Batting Team : "
                + resultSet.getString("batting_team")
        );

        System.out.println(
            "Bowling Team : "
                + resultSet.getString("bowling_team")
        );

        System.out.println();

        System.out.println(
            "Score : "
                + resultSet.getInt("total_runs")
                + "/"
                + resultSet.getInt("total_wickets")
        );

        System.out.println(
            "Overs : "
                + resultSet.getDouble("overs_played")
        );

        System.out.println("----------------------------------");
      }

    } catch (SQLException e) {
        System.out.println("Database error: "+e.getMessage());
      }
  }

  private boolean matchExists(int matchId) {

    String query = matchRepository.selectAllFromMatch();

    try {

      PreparedStatement preparedStatement =
          connection.prepareStatement(query);

      preparedStatement.setInt(1, matchId);

      ResultSet resultSet =
          preparedStatement.executeQuery();

      return resultSet.next();

    } catch (SQLException e) {
        System.out.println("Database error: "+e.getMessage());
      }

    return false;
  }

  private boolean inningsExists(int matchId, int inningsNumber) {

    String query = inningsScoringRepository.selectAllFromInnings();

    try {

      PreparedStatement preparedStatement =
          connection.prepareStatement(query);

      preparedStatement.setInt(1, matchId);
      preparedStatement.setInt(2, inningsNumber);

      ResultSet resultSet =
          preparedStatement.executeQuery();

      return resultSet.next();

    } catch (SQLException e) {
        System.out.println("Database error: "+e.getMessage());
      }

    return false;
  }

  private void viewMatchTeams(int matchId) {

    String query = matchTeamRepository.matchDetails();

    try {

      PreparedStatement preparedStatement =
          connection.prepareStatement(query);

      preparedStatement.setInt(1, matchId);

      ResultSet resultSet =
          preparedStatement.executeQuery();

      while (resultSet.next()) {

        System.out.println(
            resultSet.getInt("team_id")
                + ". "
                + resultSet.getString("team_name")
        );
      }

    } catch (SQLException e) {
        System.out.println("Database error: "+e.getMessage());
      }
  }

  private int getInningsId() {

    String query = inningsScoringRepository.checkInningsExists();

    try {

      PreparedStatement preparedStatement =
          connection.prepareStatement(query);

      ResultSet resultSet =
          preparedStatement.executeQuery();

      if (resultSet.next()) {
        return resultSet.getInt("innings_id");
      }

    } catch (SQLException e) {
        System.out.println("Database error: "+e.getMessage());
      }

    return -1;
  }

  private boolean inningsExists(int inningsId) {

    String query = inningsScoringRepository.selectInningId();

    try {

      PreparedStatement preparedStatement =
          connection.prepareStatement(query);

      preparedStatement.setInt(1, inningsId);

      ResultSet resultSet =
          preparedStatement.executeQuery();

      return resultSet.next();

    } catch (SQLException e) {
        System.out.println("Database error: "+e.getMessage());
      }

    return false;
  }

}
