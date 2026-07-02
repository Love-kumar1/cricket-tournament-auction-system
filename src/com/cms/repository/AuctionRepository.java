package com.cms.repository;

public class AuctionRepository {

  public String insertAuction() {
    return "INSERT INTO auction (auction_name, auction_year, auction_type, tournament_id) VALUES (?, ?, ?, ?);";
  }

  public String selectAucId() {
    return "SELECT auction_id FROM auction ORDER BY auction_id DESC LIMIT 1;";
  }

  public String selectAllFromAuction() {
    return "SELECT * FROM auction WHERE auction_id = ?;";
  }

}
