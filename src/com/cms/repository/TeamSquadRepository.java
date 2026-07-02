package com.cms.repository;

public class TeamSquadRepository {

  public String insertTeamSquad() {
    return "INSERT INTO team_squad (team_id, player_id, tournament_id, team_role) VALUES (?, ?, ?, ?);";
  }

  public String squadDetails() {
    return """
          SELECT ts.squad_id, p.player_name, p.player_country, p.player_role, ts.team_role
          FROM team_squad ts
          JOIN player p ON ts.player_id = p.player_id
          WHERE ts.team_id = ? AND ts.tournament_id = ?
          ORDER BY ts.team_role;
        """;
  }

  public String selectAllFromTeamSquad(){
    return "SELECT * FROM team_squad WHERE player_id = ? AND tournament_id = ?;";
  }

  public String hasAlreadyRole(){
    return "SELECT COUNT(*) FROM team_squad WHERE team_id = ? AND tournament_id = ? AND team_role = ?::player_team_role;";
  }

}
