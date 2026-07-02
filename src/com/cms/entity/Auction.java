    package com.cms.entity;

    import com.cms.repository.*;
    import com.cms.utils.InputValidator;

    import java.math.BigDecimal;
    import java.sql.*;
    import java.util.Scanner;

    public class Auction {
      private Connection connection;
      private Scanner input;
      private Tournament tournament;



      private final TeamAuctionRepository teamAuctionRepository;
      private final PlayerTeamAuctionRepository playerTeamAuctionRepository;
      private final AuctionRepository auctionRepository;
      private final PlayerRepository playerRepository;
      private final TeamRepository teamRepository;
      private final TournamentRepository tournamentRepository;



      public Auction(
          Connection connection,
          Scanner input,
          TeamAuctionRepository teamAuctionRepository,
          PlayerTeamAuctionRepository playerTeamAuctionRepository,
          AuctionRepository auctionRepository,
          PlayerRepository playerRepository,
          TeamRepository teamRepository,
          TournamentRepository tournamentRepository,
          Tournament tournament
          
      ) {
        this.connection = connection;
        this.input = input;

        this.teamAuctionRepository = teamAuctionRepository;
        this.playerTeamAuctionRepository = playerTeamAuctionRepository;
        this.auctionRepository = auctionRepository;
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
        this.tournamentRepository = tournamentRepository;

        this.tournament = tournament;




      }

      public void createAuction() {
        String query = auctionRepository.insertAuction();

        System.out.print("Enter Auction Name: ");
        String aucName = input.nextLine();
        if (!InputValidator.isValidAuctionName(aucName)) {
          return;
        }

        System.out.print("Enter Auction Year: ");
        int aucYear = input.nextInt();
        input.nextLine();
        if (!InputValidator.isValidAuctionYear(aucYear)) {
          return;
        }

        System.out.println("Enter Auction Type ");
        System.out.print("  Options(mega / mini): ");
        String aucType = input.nextLine().toLowerCase();
        if (!InputValidator.isValidAuctionType(aucType)) {
          return;
        }

        System.out.println();

        viewUpcomingTournaments();
        System.out.print("Link to Tournament ID: ");
        int lId = input.nextInt();
        if (!InputValidator.isValidId(lId)) {
          return;
        }

        try {
          PreparedStatement preparedStatement = connection.prepareStatement(query);
          preparedStatement.setString(1, aucName);
          preparedStatement.setInt(2, aucYear);
          preparedStatement.setObject(3, aucType, java.sql.Types.OTHER);
          preparedStatement.setInt(4, lId);

          int rowsAffected = preparedStatement.executeUpdate();
          if (rowsAffected > 0) {
            System.out.println("✅ Auction " + aucName + " created successfully! [ID: " + getAuctionId() + "]");
          } else {
            System.out.println("❌ Auction Creation failed..!");
          }
        } catch (SQLException e) {
          System.out.println("Database error: "+e.getMessage());
        }

      }

      public void setTeamWallet() {
        String choice;
        System.out.println("--- SET TEAM WALLET ---");
        do {
          String query = teamAuctionRepository.insertTeamAuction();

          System.out.print("Enter Auction ID: ");
          int aId = input.nextInt();

          System.out.println("Auction: " + getAuctionName(aId) + " [ID: " + aId + "]");
          System.out.println();

          viewAvailTeams();
          System.out.println();
          System.out.println("Add Team to Auction ");

          System.out.print("  Enter Team id: ");
          int tId = input.nextInt();
          input.nextLine();

          System.out.print("  Enter Team wallet: ");
          BigDecimal wallet = input.nextBigDecimal();
          input.nextLine();

          try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setBigDecimal(1, wallet);
            preparedStatement.setInt(2, tId);
            preparedStatement.setInt(3, aId);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
              System.out.println();
              System.out.println("✅ Team added to auction with wallet ₹" + wallet + "...");
            } else {
              System.out.println();
              System.out.println("failed to add team to auction....!");
              System.out.println("failed to set team wallet...!");
            }

          } catch (SQLException e) {
          System.out.println("Database error: "+e.getMessage());
        }

          System.out.print("Add another team? (y/n): ");
          choice = input.next();

        } while (!choice.equals("n"));

      }

      public void conductAuction() {
        System.out.print("Enter Auction ID: ");
        int auctionId = input.nextInt();

        System.out.println("Auction: "+getAuctionName(auctionId)+" [ID: "+auctionId+"]");
        System.out.println();
        System.out.println("loading available players....");
        System.out.println("loading team wallets....");
        System.out.println();

        try {
          String playerQuery = playerRepository.showPlayer();
          PreparedStatement playerStmt = connection.prepareStatement(playerQuery);
          playerStmt.setInt(1, auctionId);
          ResultSet rs = playerStmt.executeQuery();

          while (rs.next()) {
            int playerId = rs.getInt("player_id");
            String playerName = rs.getString("player_name");
            String playerCountry = rs.getString("player_country");
            String playerRole = rs.getString("player_role");

            System.out.println("=========== PLAYER UP FOR BID ============");
            System.out.println("Player : " + playerName);
            System.out.println("Country : " + playerCountry);
            System.out.println("Role : " + playerRole);

            System.out.print("Enter Base Price: ");
            double basePrice = input.nextDouble();

            teamWallets(auctionId);

            System.out.print("Enter Acquiring Team ID (or 0 = unsold): ");
            int acquiringTeamId = input.nextInt();

            if (acquiringTeamId == 0) {
              String unsoldQuery = playerTeamAuctionRepository.insertUnsoldPlayerTeamAuc();
              PreparedStatement unsoldStmt = connection.prepareStatement(unsoldQuery);
              unsoldStmt.setInt(1, playerId);
              unsoldStmt.setInt(2, auctionId);
              unsoldStmt.setDouble(3, basePrice);
              unsoldStmt.executeUpdate();
              unsoldStmt.close();
              System.out.println("✅ Player marked as Unsold.");
            }
            else {
              System.out.print("Enter Sold Price : ");
              double soldPrice = input.nextDouble();
              System.out.print("Enter Acquisition Type (retain | pick): ");
              String acquisitionType = input.next().toLowerCase();
              System.out.println("Enter Team Role ");
              System.out.print("	options (player | captain | vice_captain): ");
              String teamRole = input.next().toLowerCase();

              String deductQuery = teamAuctionRepository.updateDeductTeamAuc();
              PreparedStatement deductStmt = connection.prepareStatement(deductQuery);
              deductStmt.setDouble(1, soldPrice);
              deductStmt.setInt(2, acquiringTeamId);
              deductStmt.setInt(3, auctionId);
              deductStmt.executeUpdate();
              deductStmt.close();

              String auctionQuery = playerTeamAuctionRepository.insertSoldPlayerTeamAuc();
              PreparedStatement auctionStmt = connection.prepareStatement(auctionQuery);
              auctionStmt.setInt(1, playerId);
              auctionStmt.setInt(2, acquiringTeamId);
              auctionStmt.setInt(3, auctionId);
              auctionStmt.setDouble(4, basePrice);
              auctionStmt.setDouble(5, soldPrice);
              auctionStmt.setObject(6, acquisitionType, java.sql.Types.OTHER);
              auctionStmt.setObject(7, teamRole, java.sql.Types.OTHER);
              auctionStmt.executeUpdate();
              auctionStmt.close();
              System.out.println("✅ "+playerName+" sold to "+identifyTeam(acquiringTeamId));
            }

            System.out.print("Continue to next player? (y/n): ");
            String choice = input.next();
            if (!choice.equalsIgnoreCase("y")) {
              break;
            }
          }
          rs.close();
          playerStmt.close();
        } catch (SQLException e) {
          System.out.println("Error: " + e.getMessage());
        }
      }

      public void viewAucResults() {
        System.out.println("--- VIEW AUCTION RESULTS ---");

        System.out.print("Enter Auction ID: ");
        int auctionId = input.nextInt();
        input.nextLine();

        String query = playerTeamAuctionRepository.selectPlayerTeamAuc();

        try {
          PreparedStatement preparedStatement = connection.prepareStatement(query);
          preparedStatement.setInt(1, auctionId);
          ResultSet resultSet = preparedStatement.executeQuery();

          Statement statement = connection.createStatement();
          ResultSet resultSet1 = statement.executeQuery(playerTeamAuctionRepository.allFromPlayerTeamAuction());
          int count = 0;
          if (resultSet1.next()) {
            count = resultSet1.getInt("count");
          }

          System.out.println("|    Player name    |    Team Name    |   Base price   |   Sold price  | Sold status |");
          System.out.println("+-------------------+-----------------+----------------+---------------+-------------+");

          while (resultSet.next()) {
            String pName = resultSet.getString("player_name");
            String tName = resultSet.getString("team_name");
            double bPrice = resultSet.getDouble("base_price");
            String sPrice = resultSet.getString("sold_price");
            String sStatus = resultSet.getString("sold_status");

            System.out.printf("| %-17s | %-15s | %-14s | %-13s | %-11s |\n", pName, tName, bPrice, sPrice, sStatus);
          }
          System.out.println("+-------------------+-----------------+----------------+---------------+-------------+");
          System.out.println("Total Records: " + count);
        } catch (SQLException e) {
          System.out.println("Database error: "+e.getMessage());
        }
      }

      private String identifyTeam(int teamId) {
        String sql = teamRepository.selectAllFromTeams();
        try {
          PreparedStatement preparedStatement = connection.prepareStatement(sql);
          preparedStatement.setInt(1, teamId);

          ResultSet resultSet = preparedStatement.executeQuery();
          if (resultSet.next()) {
            String tName = resultSet.getString("team_name");
            return tName;

          }else {
            System.out.println("No Team Id found for given ID: "+teamId);
          }
        } catch (SQLException e) {
          System.out.println("Database error: "+e.getMessage());
        }
        return null;
      }

      private void teamWallets(int aucId) {
        String query = teamAuctionRepository.TeamWalletDetails();

        try {
          PreparedStatement preparedStatement = connection.prepareStatement(query);
          preparedStatement.setInt(1, aucId);

          ResultSet resultSet = preparedStatement.executeQuery();
          while (resultSet.next()) {
            int tId = resultSet.getInt("team_id");
            String tName = resultSet.getString("team_name");
            BigDecimal tWallet = resultSet.getBigDecimal("team_total_wallet");

            System.out.println("   [ID: " + tId + "] " + tName + "  ->" + tWallet);
          }
          resultSet.close();
          preparedStatement.close();
        } catch (SQLException e) {
          System.out.println("Database error: "+e.getMessage());
        }
      }

      private void viewUpcomingTournaments() {
        System.out.println("Available Tournaments");
        String query = tournamentRepository.upcomingTournaments();

        try {
          PreparedStatement preparedStatement = connection.prepareStatement(query);
          ResultSet resultSet = preparedStatement.executeQuery();

          int serial = 0;

          while (resultSet.next()) {
            serial++;
            String tName = resultSet.getString("tournament_name");
            int tId = resultSet.getInt("tournament_id");

            System.out.println("  " + serial + ". " + tName + " [ID: " + tId + "]");
          }
        } catch (SQLException e) {
          System.out.println("Database error: "+e.getMessage());
        }
      }

      private void viewAvailTeams() {
        System.out.println("Available Teams");
        String query = teamRepository.selectAllFromTeams();

        try {
          PreparedStatement preparedStatement = connection.prepareStatement(query);
          ResultSet resultSet = preparedStatement.executeQuery();

          int serial = 0;

          while (resultSet.next()) {
            serial++;
            String tName = resultSet.getString("team_name");
            int tId = resultSet.getInt("team_id");

            System.out.println("  " + serial + ". " + tName + " [ID: " + tId + "]");
          }
        }catch (SQLException e) {
          System.out.println("Database error: "+e.getMessage());
        }
      }

      private int getAuctionId() {
        String query = auctionRepository.selectAucId();
        try {
          PreparedStatement preparedStatement = connection.prepareStatement(query);
          ResultSet resultSet = preparedStatement.executeQuery();

          if (resultSet.next()) {
            int id = resultSet.getInt("auction_id");
            return id;
          } else {
            return 0;
          }
        } catch (SQLException e) {
          System.out.println("Database error: "+e.getMessage());
        }
        return 0;

      }

      private String getAuctionName(int id) {
        String query = auctionRepository.selectAllFromAuction();
        try {
          PreparedStatement preparedStatement = connection.prepareStatement(query);
          preparedStatement.setInt(1, id);
          ResultSet resultSet = preparedStatement.executeQuery();

          if (resultSet.next()) {
            String aucName = resultSet.getString("auction_name");
            return aucName;
          } else {
            return null;
          }
        } catch (SQLException e) {
          System.out.println("Database error: "+e.getMessage());
        }
        return null;

      }
    }
