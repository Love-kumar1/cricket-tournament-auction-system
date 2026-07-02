package com.cms.config;


import com.cms.repository.*;

public class DBConfig {

  private String url = "jdbc:postgresql://localhost:5432/tournament_auction_project";
  private String username = "postgres";
  private String password = "postgres";
  private String driver = "org.postgresql.Driver";

  private AuctionRepository auctionRepository;
  private PlayerRepository playerRepository;
  private TournamentRepository tournamentRepository;
  private PlayerTeamAuctionRepository playerTeamAuctionRepository;
  private TeamAuctionRepository teamAuctionRepository;
  private TeamRepository teamRepository;
  private Innings_ScoringRepository inningsScoringRepository;
  private MatchRepository matchRepository;
  private MatchTeamRepository matchTeamRepository;
  private StaffRepository staffRepository;
  private TeamSquadRepository teamSquadRepository;
  private TeamStaffContractRepository teamStaffContractRepository;


public DBConfig() {
  this.tournamentRepository = new TournamentRepository();
  this.auctionRepository = new AuctionRepository();
  this.playerRepository = new PlayerRepository();
  this.playerTeamAuctionRepository = new PlayerTeamAuctionRepository();
  this.teamAuctionRepository = new TeamAuctionRepository();
  this.teamRepository = new TeamRepository();
  this.inningsScoringRepository = new Innings_ScoringRepository();
  this.matchRepository = new MatchRepository();
  this.matchTeamRepository = new MatchTeamRepository();
  this.staffRepository = new StaffRepository();
  this.teamSquadRepository = new TeamSquadRepository();
  this.teamStaffContractRepository = new TeamStaffContractRepository();

}

  public DBConfig(
      String url,
      String username,
      String password,
      String driver,

      AuctionRepository auctionRepository,
      PlayerRepository playerRepository,
      TournamentRepository tournamentRepository,
      PlayerTeamAuctionRepository playerTeamAuctionRepository,
      TeamAuctionRepository teamAuctionRepository,
      TeamRepository teamRepository,
      Innings_ScoringRepository inningsScoringRepository,
      MatchRepository matchRepository,
      MatchTeamRepository matchTeamRepository,
      StaffRepository staffRepository,
      TeamSquadRepository teamSquadRepository,
      TeamStaffContractRepository teamStaffContractRepository
  ) {
    this.url = url;
    this.username = username;
    this.password = password;
    this.driver = driver;

    this.tournamentRepository = tournamentRepository;
    this.auctionRepository = auctionRepository;
    this.playerRepository = playerRepository;
    this.playerTeamAuctionRepository = playerTeamAuctionRepository;
    this.teamAuctionRepository = teamAuctionRepository;
    this.teamRepository = teamRepository;
    this.inningsScoringRepository = inningsScoringRepository;
    this.matchRepository = matchRepository;
    this.matchTeamRepository = matchTeamRepository;
    this.staffRepository = staffRepository;
    this.teamSquadRepository = teamSquadRepository;
    this.teamStaffContractRepository = teamStaffContractRepository;

  }


  public String getUrl() {
    return url;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getDriver() {
    return driver;
  }

  public  TournamentRepository getTournamentRepository() {
    return tournamentRepository;
  }

  public AuctionRepository getAuctionRepository() {
    return auctionRepository;
  }

  public PlayerRepository getPlayerRepository() {
    return playerRepository;
  }

  public PlayerTeamAuctionRepository getPlayerTeamAuctionRepository() {
    return playerTeamAuctionRepository;
  }

  public TeamAuctionRepository getTeamAuctionRepository() {
    return teamAuctionRepository;
  }

  public TeamRepository getTeamRepository() {
    return teamRepository;
  }

  public Innings_ScoringRepository getInningsScoringRepository() {
    return inningsScoringRepository;
  }

  public MatchRepository getMatchRepository() {
    return matchRepository;
  }

  public MatchTeamRepository getMatchTeamRepository() {
    return matchTeamRepository;
  }

  public StaffRepository getStaffRepository() {
    return staffRepository;
  }

  public TeamSquadRepository getTeamSquadRepository() {
    return teamSquadRepository;
  }

  public TeamStaffContractRepository getTeamStaffContractRepository() {
    return teamStaffContractRepository;
  }


}

