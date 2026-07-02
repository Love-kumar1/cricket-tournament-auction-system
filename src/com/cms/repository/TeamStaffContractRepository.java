package com.cms.repository;

public class TeamStaffContractRepository {

  public String insertTeamStaffContract(){
    return  """
        INSERT INTO team_staff_contract
        (
            team_id,
            staff_id,
            role,
            duration_from,
            duration_till
        )
        SELECT
            ?,
            staff_id,
            staff_role,
            ?,
            ?
        FROM staff
        WHERE staff_id = ?;
        """;

  }


  public String selectTeamStaffContract(){
    return """
      SELECT
        tsc.contract_id,
        t.team_name,
        s.staff_name,
        tsc.role,
        tsc.duration_from,
        tsc.duration_till
      FROM team_staff_contract tsc
      JOIN team t
        ON tsc.team_id = t.team_id
      JOIN staff s
        ON tsc.staff_id = s.staff_id
      ORDER BY tsc.contract_id;
      """;
  }


}
