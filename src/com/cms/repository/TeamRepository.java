package com.cms.repository;

public class TeamRepository {

  public String insertTeam(){
    return "INSERT INTO team(team_name, team_city, team_owner) VALUES (?, ? ,?);";
  }

  public String selectAllFromTeams(){
    return "SELECT * FROM team WHERE team_id = ?;";
  }

  public String selectAllTeams(){
    return "SELECT * FROM team;";
  }

  public String selectOrderedFromTeams(){
    return "SELECT * FROM team ORDER BY team_id;";
  }

  public String selectLastTeam(){
    return "SELECT * FROM team ORDER BY team_id DESC LIMIT 1;";
  }

  public String deleteTeam(){
    return "DELETE FROM team WHERE team_id = ?;";
  }

  public String updateTeamOwner(){
    return "UPDATE team SET team_owner = ? WHERE team_id = ?;";
  }

  public String updateTeamCity(){
    return "UPDATE team SET team_city = ? WHERE team_id = ?;";
  }

  public String linkManagerToTeam(){
    return "UPDATE team SET manager_user_id = ? WHERE team_id = ?;";
  }




}
