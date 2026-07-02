package com.cms.repository;

public class Innings_ScoringRepository {

  public String insertInning() {
    return """
          INSERT INTO innings
          (innings_number, match_id, batting_team_id, bowling_team_id)
          VALUES (?, ?, ?, ?);
        """;
  }

  public String selectAllFromInnings() {
    return "SELECT * FROM innings WHERE match_id = ? AND innings_number = ?;";
  }

  public String selectInningId() {
    return """
        SELECT innings_id
        FROM innings
        ORDER BY innings_id DESC
        LIMIT 1;
        """;
  }

  public String scorecardQuery() {
    return """
          SELECT
              bt.team_name AS batting_team,
              bw.team_name AS bowling_team,
              COALESCE(isc.total_runs, 0) AS total_runs,
              COALESCE(isc.total_wickets, 0) AS total_wickets,
              COALESCE(isc.overs_played, 0) AS overs_played
          FROM innings i
        
          JOIN team bt
              ON i.batting_team_id = bt.team_id
        
          JOIN team bw
              ON i.bowling_team_id = bw.team_id
        
          LEFT JOIN innings_score isc
              ON i.innings_id = isc.innings_id
        
          WHERE i.innings_id = ?;
        """;
  }

  public String checkInningsExists() {
    return "SELECT innings_id FROM innings WHERE innings_id = ?;";
  }
}
