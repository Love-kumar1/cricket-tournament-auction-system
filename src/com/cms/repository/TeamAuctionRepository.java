package com.cms.repository;

public class TeamAuctionRepository {

  public String insertTeamAuction() {
    return "INSERT INTO team_auction (team_total_wallet, team_id, auction_id) VALUES (?, ?, ?);";
  }

  public String updateDeductTeamAuc() {
    return """
        UPDATE team_auction SET team_total_wallet = team_total_wallet - ? 
        WHERE team_id = ? AND auction_id = ?
        """;
  }

  public String TeamWalletDetails() {
    return """
        SELECT t.team_id, t.team_name, ta.team_total_wallet FROM team t JOIN team_auction ta ON t.team_id = ta.team_id WHERE auction_id = ?;
        """;
  }


  public String activeAuctionTeams() {
    return """
        SELECT DISTINCT t.team_id, t.team_name
        FROM team t
        JOIN team_auction ta ON t.team_id = ta.team_id
        JOIN auction am ON ta.auction_id = am.auction_id
        WHERE am.tournament_id = ?;
        """;
  }






}
