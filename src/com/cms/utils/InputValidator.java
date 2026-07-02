  package com.cms.utils;

  public class InputValidator {

    // ✅ TOURNAMENT VALIDATIONS

    public static boolean isValidTournamentName(String name) {
      if (name == null || name.trim().isEmpty()) {
        System.out.println("❌ Tournament name cannot be empty!");
        return false;
      }
      if (name.length() < 2) {
        System.out.println("❌ Tournament name must be at least 2 characters!");
        return false;
      }
      if (name.length() > 50) {
        System.out.println("❌ Tournament name cannot exceed 50 characters!");
        return false;
      }
      return true;
    }

    public static boolean isValidYear(int year) {
      if (year < 2000 || year > 2100) {
        System.out.println("❌ Year must be between 2000-2100!");
        return false;
      }
      return true;
    }

    public static boolean isValidCountry(String country) {
      if (country == null || country.trim().isEmpty()) {
        System.out.println("❌ Country cannot be empty!");
        return false;
      }
      if (country.length() < 2) {
        System.out.println("❌ Country name must be at least 2 characters!");
        return false;
      }
      return true;
    }

    public static boolean isValidTournamentStatus(String status) {
      if (status == null || status.trim().isEmpty()) {
        System.out.println("❌ Status cannot be empty!");
        return false;
      }
      String statusLower = status.toLowerCase();
      if (!statusLower.equals("upcoming") && !statusLower.equals("ongoing") && !statusLower.equals("completed")) {
        System.out.println("❌ Status must be: upcoming, ongoing, or completed!");
        return false;
      }
      return true;
    }

    // ✅ TEAM VALIDATIONS

    public static boolean isValidTeamName(String name) {
      if (name == null || name.trim().isEmpty()) {
        System.out.println("❌ Team name cannot be empty!");
        return false;
      }
      if (name.length() < 2) {
        System.out.println("❌ Team name must be at least 2 characters!");
        return false;
      }
      if (name.length() > 30) {
        System.out.println("❌ Team name cannot exceed 30 characters!");
        return false;
      }
      return true;
    }

    public static boolean isValidCity(String city) {
      if (city == null || city.trim().isEmpty()) {
        System.out.println("❌ City cannot be empty!");
        return false;
      }
      if (city.length() < 2) {
        System.out.println("❌ City name must be at least 2 characters!");
        return false;
      }
      return true;
    }

    public static boolean isValidOwner(String owner) {
      if (owner == null || owner.trim().isEmpty()) {
        System.out.println("❌ Owner name cannot be empty!");
        return false;
      }
      if (owner.length() < 2) {
        System.out.println("❌ Owner name must be at least 2 characters!");
        return false;
      }
      return true;
    }

    // ✅ PLAYER VALIDATIONS

    public static boolean isValidPlayerName(String name) {
      if (name == null || name.trim().isEmpty()) {
        System.out.println("❌ Player name cannot be empty!");
        return false;
      }
      if (name.length() < 2) {
        System.out.println("❌ Player name must be at least 2 characters!");
        return false;
      }
      if (name.length() > 50) {
        System.out.println("❌ Player name cannot exceed 50 characters!");
        return false;
      }
      return true;
    }

    public static boolean isValidPlayerAge(int age) {
      if (age < 15 || age > 70) {
        System.out.println("❌ Player age must be between 15-70!");
        return false;
      }
      return true;
    }

    public static boolean isValidPlayerRole(String role) {
      if (role == null || role.trim().isEmpty()) {
        System.out.println("❌ Player role cannot be empty!");
        return false;
      }
      String roleLower = role.toLowerCase();
      if (!roleLower.equals("batsman") && !roleLower.equals("bowler")
          && !roleLower.equals("all rounder") && !roleLower.equals("wicket keeper")) {
        System.out.println("❌ Player role must be: batsman, bowler, all rounder, or wicket keeper!");
        return false;
      }
      return true;
    }

    // ✅ AUCTION VALIDATIONS

    public static boolean isValidAuctionName(String name) {
      if (name == null || name.trim().isEmpty()) {
        System.out.println("❌ Auction name cannot be empty!");
        return false;
      }
      return true;
    }

    public static boolean isValidAuctionYear(int year) {
      if (year < 2020 || year > 2100) {
        System.out.println("❌ Auction year must be between 2020-2100!");
        return false;
      }
      return true;
    }

    public static boolean isValidAuctionType(String type) {
      if (type == null || type.trim().isEmpty()) {
        System.out.println("❌ Auction type cannot be empty!");
        return false;
      }
      String typeLower = type.toLowerCase();
      if (!typeLower.equals("mega") && !typeLower.equals("mini")) {
        System.out.println("❌ Auction type must be: mega or mini!");
        return false;
      }
      return true;
    }

    // ✅ MATCH VALIDATIONS

    public static boolean isValidVenue(String venue) {
      if (venue == null || venue.trim().isEmpty()) {
        System.out.println("❌ Venue cannot be empty!");
        return false;
      }
      if (venue.length() < 2) {
        System.out.println("❌ Venue name must be at least 2 characters!");
        return false;
      }
      return true;
    }

    public static boolean isValidDate(String date) {
      if (date == null || date.trim().isEmpty()) {
        System.out.println("❌ Date cannot be empty!");
        return false;
      }
      // Check YYYY-MM-DD format
      if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
        System.out.println("❌ Date format must be YYYY-MM-DD!");
        return false;
      }
      return true;
    }

    public static boolean isValidTime(String time) {
      if (time == null || time.trim().isEmpty()) {
        System.out.println("❌ Time cannot be empty!");
        return false;
      }
      // Check HH:MM format
      if (!time.matches("\\d{2}:\\d{2}")) {
        System.out.println("❌ Time format must be HH:MM!");
        return false;
      }
      return true;
    }

    public static boolean isValidMatchType(String type) {
      if (type == null || type.trim().isEmpty()) {
        System.out.println("❌ Match type cannot be empty!");
        return false;
      }
      String typeLower = type.toLowerCase();
      if (!typeLower.equals("t20") && !typeLower.equals("odi") && !typeLower.equals("test")) {
        System.out.println("❌ Match type must be: t20, odi, or test!");
        return false;
      }
      return true;
    }

    // ✅ STAFF VALIDATIONS

    public static boolean isValidStaffName(String name) {
      if (name == null || name.trim().isEmpty()) {
        System.out.println("❌ Staff name cannot be empty!");
        return false;
      }
      if (name.length() < 2) {
        System.out.println("❌ Staff name must be at least 2 characters!");
        return false;
      }
      return true;
    }

    public static boolean isValidExperience(int experience) {
      if (experience < 0 || experience > 60) {
        System.out.println("❌ Experience must be between 0-60 years!");
        return false;
      }
      return true;
    }

    public static boolean isValidStaffRole(String role) {
      if (role == null || role.trim().isEmpty()) {
        System.out.println("❌ Staff role cannot be empty!");
        return false;
      }
      String roleLower = role.toLowerCase();
      if (!roleLower.equals("head_coach") && !roleLower.equals("batting_coach")
          && !roleLower.equals("bowling_coach") && !roleLower.equals("fielding_coach")
          && !roleLower.equals("team_manager") && !roleLower.equals("physio")
          && !roleLower.equals("analyst") && !roleLower.equals("trainer")) {
        System.out.println("❌ Invalid staff role! Must be: head_coach, batting_coach, bowling_coach, fielding_coach, team_manager, physio, analyst, or trainer!");
        return false;
      }
      return true;
    }

    // ✅ AUTHENTICATION VALIDATIONS

    public static boolean isValidEmail(String email) {
      if (email == null || email.trim().isEmpty()) {
        System.out.println("❌ Email cannot be empty!");
        return false;
      }
      // Simple email validation
      if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
        System.out.println("❌ Invalid email format!");
        return false;
      }
      return true;
    }

    public static boolean isValidPassword(String password) {
      if (password == null || password.isEmpty()) {
        System.out.println("❌ Password cannot be empty!");
        return false;
      }
      if (password.length() < 6) {
        System.out.println("❌ Password must be at least 6 characters!");
        return false;
      }
      return true;
    }

    public static boolean isValidFullName(String name) {
      if (name == null || name.trim().isEmpty()) {
        System.out.println("❌ Full name cannot be empty!");
        return false;
      }
      if (name.length() < 2) {
        System.out.println("❌ Full name must be at least 2 characters!");
        return false;
      }
      return true;
    }

    // ✅ GENERAL VALIDATIONS

    public static boolean isValidId(int id) {
      if (id <= 0) {
        System.out.println("❌ ID must be greater than 0!");
        return false;
      }
      return true;
    }

    public static boolean isNotEmpty(String value) {
      if (value == null || value.trim().isEmpty()) {
        System.out.println("❌ This field cannot be empty!");
        return false;
      }
      return true;
    }
  }