package com.cms.repository;

public class TournamentRepository {

  public String upcomingTournaments() {
    return "SELECT * FROM tournament WHERE tournament_status = 'upcoming';";
  }

  public String upcoming_ongoingTournaments() {
    return """
        SELECT tournament_id, tournament_name, tournament_year
        FROM tournament
        WHERE tournament_status IN ('upcoming'::current_status, 'ongoing'::current_status);
        """;
  }

  public String insertTournaments() {
    return "INSERT INTO tournament(tournament_name, tournament_year, tournament_country, tournament_status) VALUES (?, ?, ? ,?);";
  }

  public String allTournaments() {
    return "SELECT * FROM tournament WHERE tournament_id = ?;";
  }

  public String viewAllTournaments() {
    return """
        SELECT
          t.tournament_id,
          t.tournament_name,
          t.tournament_year, 
          t.tournament_country, 
          t.tournament_status, 
          tm.team_name AS winner
        FROM tournament t
          LEFT JOIN team tm
            ON t.tournament_winner_team_id = tm.team_id;
        """;
  }

  public String updateTournament() {
    return """
        UPDATE tournament SET tournament_winner_team_id = ?,  tournament_status = 'completed' WHERE tournament_id = ?;
      """;
  }

  public String updateTournamentStatus() {
    return """
        UPDATE tournament SET tournament_status = ? WHERE tournament_id = ?;
      """;
  }

  public String deleteTournaments() {
    return "DELETE FROM tournament WHERE tournament_id = ?;";
  }


  public String getLastTournamentId() {
    return "SELECT tournament_id FROM tournament ORDER BY tournament_id DESC LIMIT 1;";
  }


}
