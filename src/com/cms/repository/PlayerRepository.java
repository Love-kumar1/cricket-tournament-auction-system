package com.cms.repository;

public class PlayerRepository {

  public String showPlayer() {
    return """
        SELECT p.player_id, p.player_name, p.player_country, p.player_role
        FROM player p WHERE p.player_id NOT IN (SELECT pta.player_id FROM player_team_auction pta WHERE pta.auction_id = ?)
        """;
  }

  public String insertPlayer() {
    return "INSERT INTO player(player_name, player_country, player_age, player_role) VALUES (?, ? ,?, ?);";
  }

  public String allPlayer() {
    return "SELECT * FROM player;";
  }

  public String removePlayer() {
    return "DELETE FROM player WHERE player_id = ?;";
  }


  public String getLastPlayerId() {
    return "SELECT player_id FROM player ORDER BY player_id DESC LIMIT 1;";
  }

  public String linkUserToPlayer(){
    return "UPDATE player SET user_id = ? WHERE player_id = ?;";
  }



}
