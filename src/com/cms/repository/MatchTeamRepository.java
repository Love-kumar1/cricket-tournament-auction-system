package com.cms.repository;

public class MatchTeamRepository {

  public String matchDetails() {
    return """
        SELECT
          t.team_id,
          t.team_name
        FROM match_team mt
          JOIN team t
            ON mt.team_id = t.team_id
        WHERE mt.match_id = ?;
        """;
  }

  public String insertMatchTeam() {
    return "INSERT INTO match_team (match_id, team_id) VALUES (?, ?);";
  }


  public String showAllMatches() {
    return """
        SELECT
            m.match_id,
            m.match_venue,
            m.match_date,
            m.match_type,
            m.match_status,
            STRING_AGG(t.team_name, ' vs ' ORDER BY mt.match_team_id) AS teams
        FROM match m
        JOIN match_team mt ON m.match_id = mt.match_id
        JOIN team t ON mt.team_id = t.team_id
        WHERE m.tournament_id = ?
        GROUP BY
            m.match_id,
            m.match_venue,
            m.match_date,
            m.match_type,
            m.match_status
        ORDER BY
            m.match_date ASC;
        """;
  }


  public String upcomingMatches() {
    return """
        SELECT m.match_id, m.match_venue, m.match_date, m.match_time, m.match_type
        FROM match m
        LEFT JOIN match_team mt ON m.match_id = mt.match_id
        WHERE mt.match_id IS NULL
          AND m.match_status = 'upcoming'::current_status
          AND m.tournament_id = ?;
        """;
  }


}
