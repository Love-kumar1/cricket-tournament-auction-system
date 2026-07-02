package com.cms.repository;

public class StaffRepository {

  public String insertStaff(){
    return """
      INSERT INTO staff
        (
          staff_name,
          staff_country,
          experience,
          staff_role
        )
      VALUES (?, ?, ?, ?::staff_role);
      """;
  }

  public String selectAllFromStaff(){
    return "SELECT * FROM staff ORDER BY staff_id;";
  }

}
