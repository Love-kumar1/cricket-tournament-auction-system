package com.cms;

import com.cms.utils.InputValidator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Authentication {

  private Connection connection;
  private Scanner input;

  public Authentication(Connection connection, Scanner input) {
    this.connection = connection;
    this.input = input;
  }


  public void register() {
    System.out.print("""
        ══════════════════════════════════════
                        REGISTER
        ══════════════════════════════════════
        """);

    String query = "INSERT INTO users(user_name, user_email, user_password, user_role) VALUES (?, ?, ?, ?);";
    System.out.print("Enter full name  : ");
    String fullName = input.nextLine();
    if (!InputValidator.isValidFullName(fullName)) {
      return;
    }

    System.out.print("Enter Email      : ");
    String email = input.nextLine();
    if (!InputValidator.isValidEmail(email)) {
      return;
    }

    System.out.print("Enter Password   : ");
    String password = input.nextLine();
    System.out.println();
    if (!InputValidator.isValidPassword(password)) {
      return;
    }

    if (!accountExists(email)) {
      System.out.println("Select your role ");
      System.out.print("""
          1. Team manager
          2. Player
          """);
      System.out.print("Enter role choice :");
      int rChoice = input.nextInt();
      input.nextLine();

      String role = null;
      switch (rChoice) {
        case 1:
          role = "Team manager";
          break;
        case 2:
          role = "Player";
          break;
        default:
          System.out.println("Wrong choice");
          return;
      }

      try {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, fullName);
        preparedStatement.setString(2, email);
        preparedStatement.setString(3, password);
        preparedStatement.setString(4, role);

        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected > 0) {
          System.out.println("You have successfully registered!");
        } else {
          System.out.println("registration Failed ! try again");
        }
      } catch (SQLException e) {
        System.out.println(e.getMessage());
      }
    } else {
      System.out.println("Account already exists continue to login or create new account...");
    }


  }

  String login() {
    System.out.print("""
        ══════════════════════════════════════
                        LOGIN
        ══════════════════════════════════════
        """);

    System.out.print("Enter Email      : ");
    String email = input.nextLine();
    System.out.println();

    if (!accountExists(email)) {
      System.out.println("account does not exist for this email");
      return null;
    } else {
      String oriPass = getPassword(email);
      System.out.print("Enter Password   : ");
      String password = input.nextLine();

      if (password.equals(oriPass)) {
        return getUserRole(email);
      }else {
        System.out.println("invalid password");
        return null;
      }
    }
  }

  private String getUserRole(String email) {
    String query = "SELECT user_role FROM users WHERE user_email = ?;";
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setString(1, email);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        return resultSet.getString("user_role");
      }
    } catch (SQLException e) {
        System.out.println("Database error: "+e.getMessage());
      }
    return null;
  }

  private String getPassword(String email) {
    String query = "SELECT * FROM users WHERE user_email = ?;";
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setString(1, email);

      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        return resultSet.getString("user_password");
      } else {
        System.out.println("no record fount");
        return null;
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return null;
  }

  private boolean accountExists(String email) {
    String query = "SELECT COUNT(*) FROM users WHERE user_email = ?;";
    try {
      PreparedStatement ps = connection.prepareStatement(query);
      ps.setString(1, email);
      ResultSet rs = ps.executeQuery();

      int count = 0;
      if (rs.next()) {
        count = rs.getInt("count");
        if (count == 0) {
          return false;
        } else {
          return true;
        }
      }
    } catch (SQLException e) {
        System.out.println("Database error: "+e.getMessage());
      }
    return false;
  }
}
