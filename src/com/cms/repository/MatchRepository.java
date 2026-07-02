package com.cms.repository;

public class MatchRepository {

  public String selectAllFromMatch() {
    return "SELECT * FROM match WHERE match_id = ?;";
  }

  public String insertMatch() {
    return """
        INSERT INTO match (match_venue, match_date, match_time, match_type, match_overs, match_status, tournament_id)
        VALUES (?, ?, ?, ?, ?, ?, ?);
        """;
  }


}
