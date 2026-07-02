package com.cms;

import com.cms.entity.*;
import com.cms.ui.AdminDashboard;
import com.cms.ui.ManagerDashboard;
import com.cms.ui.PlayerDashboard;
import com.cms.ui.ViewerDashboard;
import com.cms.repository.*;
import com.cms.config.DBConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import static com.cms.utils.AppConstants.*;

public class Main {
  static Connection connection;
  static Scanner input;
  private static String password;

  public static void main(String[] args) {

    DBConfig dbConfig = new DBConfig();

    String url = dbConfig.getUrl();
    String username = dbConfig.getUsername();
    String password = dbConfig.getPassword();
    String driver = dbConfig.getDriver();

    TournamentRepository tournamentRepository = dbConfig.getTournamentRepository();
    AuctionRepository auctionRepository = dbConfig.getAuctionRepository();
    PlayerRepository playerRepository = dbConfig.getPlayerRepository();
    PlayerTeamAuctionRepository playerTeamAuctionRepository = dbConfig.getPlayerTeamAuctionRepository();
    TeamAuctionRepository teamAuctionRepository = dbConfig.getTeamAuctionRepository();
    TeamRepository teamRepository = dbConfig.getTeamRepository();
    Innings_ScoringRepository inningsScoringRepository = dbConfig.getInningsScoringRepository();
    MatchRepository matchRepository = dbConfig.getMatchRepository();
    MatchTeamRepository matchTeamRepository = dbConfig.getMatchTeamRepository();
    StaffRepository staffRepository = dbConfig.getStaffRepository();
    TeamSquadRepository teamSquadRepository = dbConfig.getTeamSquadRepository();
    TeamStaffContractRepository teamStaffContractRepository = dbConfig.getTeamStaffContractRepository();

    try {
      Class.forName(driver);
    } catch (ClassNotFoundException e) {
      System.out.println("drivers not loaded");
      System.out.println(e.getMessage());
    }

    try {
      connection = DriverManager.getConnection(url, username, password);
      input = new Scanner(System.in);



      Team team = new Team(connection, input, teamRepository);
      Tournament tournament = new Tournament(
          connection, input, tournamentRepository, team);
      Player player = new Player(connection, input, playerRepository);
      Match match = new Match(
          connection, input, matchRepository, matchTeamRepository, tournamentRepository, teamAuctionRepository);
      Auction auction = new Auction(
          connection, input, teamAuctionRepository, playerTeamAuctionRepository, auctionRepository,
          playerRepository, teamRepository, tournamentRepository, tournament);
      Squad squad = new Squad(
          input, connection, teamSquadRepository, tournamentRepository, teamAuctionRepository);
      InningsScoring InnAndScore = new InningsScoring(
          connection, input, match, inningsScoringRepository, matchRepository, matchTeamRepository);
      Staff staff = new Staff(
          connection, input, team, staffRepository, teamStaffContractRepository, teamRepository);

      Authentication auth = new Authentication(connection, input);

      boolean runSystem = true;
      while (runSystem) {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║    🏏 CRICKET TOURNAMENT & AUCTION MANAGEMENT SYSTEM  ║║");
        System.out.println("║              Terminal Edition |  JDBC + Java          ║║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");

        System.out.println("══════════════════  Developer: Love Kumar  ═══════════════════");
        System.out.println("    Initialising Database connection...");
        System.out.println("    ✅ Connected to: database @ localhost:5432");
        System.out.println("══════════════════════════════════════════════════════════════\n");

        System.out.println("        WELCOME — HOW TO CONTINUE     ");
        System.out.println("══════════════════════════════════════");
        System.out.println("1. Register  (create a new account)");
        System.out.println("2. Login     (existing account)");
        System.out.println("3. Continue as GUEST (no login)");
        System.out.println("0. Exit\n");
        System.out.println("══════════════════════════════════════");
        System.out.print("Enter your choice: ");
        int choice1 = input.nextInt();
        input.nextLine();

        String userRole = null;

        switch (choice1) {
          case 1:
            auth.register();
            break;

          case 2:
            userRole = auth.login();

            if (userRole != null) {
              System.out.println(LOGIN_DASHBOARD_MSG);

              if (userRole.equalsIgnoreCase(ADMIN_ROLE)) {
                AdminDashboard adminDb = new AdminDashboard(tournament, team, player, auction, squad, match, InnAndScore, staff, input);
                adminDb.openAdminDashboard();

              } else if (userRole.equalsIgnoreCase(MANAGER_ROLE)) {
                ManagerDashboard managerDb = new ManagerDashboard(squad, input);
                managerDb.openManagerDashboard();

              } else if (userRole.equalsIgnoreCase(PLAYER_ROLE)) {
                PlayerDashboard playerDb = new PlayerDashboard(match, squad, auction, input);
                playerDb.openPlayerDashboard();

              } else {
                System.out.println(WRONG_ROLE_MSG);
              }
            } else {
              System.out.println(FAILED_LOGIN_MSG);
            }
            break;

          case 3:
            ViewerDashboard viewerDb = new ViewerDashboard(tournament, team, player, squad, match, InnAndScore, input);
            viewerDb.openViewerDashboard();
            break;

          case 0:
            runSystem = false;
            System.out.println("\n Thank you Love Kumar! closing system...");
            break;

          default:
            System.out.println("Invalid Choice! Please select 0, 1, 2, or 3.");
        }
      }

    } catch (SQLException e) {
      System.out.println("Connection not established");
      System.out.println(e.getMessage());
    }
  }
}