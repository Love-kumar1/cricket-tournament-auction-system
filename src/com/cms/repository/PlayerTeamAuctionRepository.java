package com.cms.repository;

public class PlayerTeamAuctionRepository {

  public String insertUnsoldPlayerTeamAuc() {
    return """
        INSERT INTO player_team_auction (player_id, auction_id, base_price, sold_price, sold_status, acquisition_type, team_role)
        VALUES (?, ?, ?, 0, 'unsold', NULL, NULL);
        """;
  }

  public String insertSoldPlayerTeamAuc() {
    return """
                INSERT INTO player_team_auction (player_id, team_id, auction_id, base_price, sold_price, sold_status, acquisition_type, team_role) VALUES (?, ?, ?, ?, ?, 'sold', ?, ?);
        """;
  }

  public String selectPlayerTeamAuc(){
    return """
        SELECT p.player_name, t.team_name, pta.base_price, pta.sold_price, pta.sold_status
        FROM player_team_auction pta
            JOIN player p ON pta.player_id = p.player_id
            LEFT JOIN team t ON pta.team_id = t.team_id
        WHERE pta.auction_id = ?
        ORDER BY pta.sold_price DESC;
        """;
  }

  public String allFromPlayerTeamAuction() {
    return "SELECT count(*) FROM player_team_auction;";
  }

  public String eligiblePlayerSelection() {
    return """
            SELECT p.player_id, p.player_name, p.player_role
                FROM player p
                JOIN player_team_auction pta ON p.player_id = pta.player_id
                WHERE pta.team_id = ?
                  AND pta.auction_id = (
                      SELECT auction_id FROM auction WHERE tournament_id = ? LIMIT 1
                  )
                  AND pta.sold_status = 'sold'::player_sold_status;
        """;
  }


}
