# 🏏 Cricket Tournament & Auction Management System
 
A **terminal-based Java application** for managing cricket tournaments end-to-end — from player auctions and team wallets, to squad building, match scheduling, and live innings scoring. Built with core **Java, JDBC, and PostgreSQL**, following a clean layered architecture (Entity → Repository → Config → UI).
 
> Think of it as a simplified, terminal version of an IPL-style auction and tournament management platform.
 
---
 
## 📌 Table of Contents
 
- [Overview](#-overview)
- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Project Architecture](#-project-architecture)
- [Database Schema](#-database-schema)
- [Role-Based Access](#-role-based-access)
- [Getting Started](#-getting-started)
- [Project Structure](#-project-structure)
- [Sample Workflow](#-sample-workflow)
- [Future Improvements](#-future-improvements)
- [Author](#-author)
---
 
## 🎯 Overview
 
This system digitizes the full lifecycle of running a cricket tournament:
 
1. **Create a tournament** and register participating teams & players.
2. **Run a player auction** — set team wallets, conduct live bidding, mark players sold/unsold.
3. **Build team squads** from auctioned players, assigning captain / vice-captain roles.
4. **Schedule matches**, assign teams, and track match status.
5. **Score innings live** and view scorecards.
6. **Manage support staff** (coaches, physios, analysts) with team contracts.
All of this is driven through clean, role-based terminal dashboards — no GUI needed, pure JDBC + PostgreSQL under the hood.
 
---
 
## ✨ Features
 
### 🏆 Tournament Management
- Create, view, update, and delete tournaments
- Track tournament status (`upcoming`, `ongoing`, `completed`) and declare winners
### 👕 Team Management
- Add, view, update, and delete teams
- Link a registered user as a **Team Manager**
### 🧍 Player Management
- Register players with role (`batsman`, `bowler`, `all-rounder`, `wicket-keeper`)
- Link a registered user as a **Player**
### 💰 Auction System
- Create mega/mini auctions linked to a tournament
- Assign starting wallets to participating teams
- Conduct live, player-by-player bidding with real-time wallet deduction
- Automatically mark players **sold** or **unsold**
- View complete auction results
### 🎽 Squad Building
- Build a tournament squad from a team's auctioned players
- Enforce **one captain** and **one vice-captain** per team, per tournament
- Prevent a player from being added to a squad twice
### 📅 Match Management
- Schedule matches with venue, date, time, and format (`T20` / `ODI` / `Test`)
- Auto-calculate overs based on match type
- Assign two teams to a match
- View full match schedules per tournament
### 🏏 Live Innings Scoring
- Start an innings with batting & bowling teams
- View a live scorecard (runs, wickets, overs)
### 🧑‍🏫 Staff & Contracts
- Add support staff (head coach, batting coach, physio, analyst, trainer, etc.)
- Assign staff to teams with contract duration
- View all active contracts
### 🔐 Authentication & Role-Based Dashboards
- Register / Login system with role selection
- Separate dashboards for **Admin**, **Team Manager**, **Player**, and **Guest/Viewer**
---
 
## 🛠 Tech Stack
 
| Layer          | Technology              |
|----------------|--------------------------|
| Language        | Java (Core Java, JDBC)  |
| Database        | PostgreSQL               |
| Data Access     | JDBC (PreparedStatement) |
| Architecture    | Layered (Entity / Repository / Config / UI) |
| Interface       | Java Console (Scanner-based) |
 
---
 
## 🏗 Project Architecture
 
The project follows a clean **layered architecture**, keeping SQL, business logic, and user interaction fully separated:
 
```
┌─────────────────────────────┐
│           UI Layer          │  ← Dashboards (Admin / Manager / Player / Viewer)
├─────────────────────────────┤
│         Entity Layer        │  ← Business logic (Tournament, Team, Auction, etc.)
├─────────────────────────────┤
│       Repository Layer      │  ← Raw SQL queries, one class per table
├─────────────────────────────┤
│         Config Layer        │  ← DB connection & dependency wiring
├─────────────────────────────┤
│         PostgreSQL DB       │  ← Persistent storage
└─────────────────────────────┘
```
 
This separation means:
- **Repositories** hold *only* SQL strings — no logic.
- **Entities** hold business rules and call repositories through JDBC.
- **UI dashboards** simply call entity methods based on user menu choices.
- **DBConfig** wires every repository together in one place.
---
 
## 🗄 Database Schema
 
The database consists of **12 core tables**, fully normalized and connected via foreign keys:
 
| Table                  | Purpose                                             |
|-------------------------|------------------------------------------------------|
| `users`                 | Registered accounts (Admin, Manager, Player)         |
| `tournament`             | Tournament details & status                          |
| `team`                   | Team details, linked to a manager                    |
| `player`                 | Player details, linked to a user                     |
| `auction`                | Auction events linked to a tournament                |
| `team_auction`           | Team wallets per auction                             |
| `player_team_auction`    | Auction bidding results (sold/unsold, price, role)   |
| `team_squad`             | Final tournament squads per team                     |
| `match`                  | Scheduled matches per tournament                     |
| `match_team`             | Teams assigned to a match                            |
| `innings`                | Innings per match (batting/bowling team)             |
| `innings_score`          | Live score per innings                               |
| `ball_by_ball`           | Ball-level match data                                |
| `staff`                  | Support staff details                                |
| `team_staff_contract`    | Staff-to-team contracts                               |
 
**Custom PostgreSQL ENUM types** are used throughout for data integrity:
`auction_type`, `current_status`, `match_type`, `player_acquisition_type`, `player_role`, `player_sold_status`, `player_team_role`, `staff_role`.
 
> 📎 Full schema with constraints: [`Tournament_auction_project.sql`](./Tournament_auction_project.sql)
 
---
 
## 🔐 Role-Based Access
 
| Role             | Dashboard Access                                                          |
|-------------------|------------------------------------------------------------------------|
| **Admin**          | Full access — tournaments, teams, players, auctions, squads, matches, scoring, staff |
| **Team Manager**   | Build & view their team's squad                                        |
| **Player**         | View auction results, their squad, and match schedules                 |
| **Guest / Viewer**  | Read-only access — tournaments, teams, players, squads, matches, live scores |
 
---
 
## 🚀 Getting Started
 
### Prerequisites
- Java JDK 17+
- PostgreSQL 13+
- PostgreSQL JDBC Driver (`postgresql-x.x.x.jar`)
### 1. Clone the repository
```bash
git clone https://github.com/<your-username>/cricket-tournament-auction-system.git
cd cricket-tournament-auction-system
```
 
### 2. Set up the database
Create a PostgreSQL database and load the schema:
```bash
createdb tournament_auction_project
psql -U postgres -d tournament_auction_project -f Tournament_auction_project.sql
```
 
### 3. Configure the connection
Update your database credentials in `DBConfig.java`:
```java
private String url = "jdbc:postgresql://localhost:5432/tournament_auction_project";
private String username = "postgres";
private String password = "your_password";
```
 
> ⚠️ For production or public repos, move credentials to environment variables instead of hardcoding them.
 
### 4. Compile and run
```bash
javac -cp .:postgresql-x.x.x.jar -d bin src/com/cms/**/*.java
java -cp bin:postgresql-x.x.x.jar com.cms.Main
```
 
### 5. Use the app
- Choose **Register** to create an account (Team Manager / Player), or
- **Login** with existing credentials, or
- Continue as **Guest** for read-only access
---
 
## 📁 Project Structure
 
```
com.cms
├── config
│   └── DBConfig.java              # DB connection + repository wiring
├── entity
│   ├── Tournament.java
│   ├── Team.java
│   ├── Player.java
│   ├── Auction.java
│   ├── Squad.java
│   ├── Match.java
│   ├── InningsScoring.java
│   └── Staff.java
├── repository
│   ├── TournamentRepository.java
│   ├── TeamRepository.java
│   ├── PlayerRepository.java
│   ├── AuctionRepository.java
│   ├── TeamAuctionRepository.java
│   ├── PlayerTeamAuctionRepository.java
│   ├── MatchRepository.java
│   ├── MatchTeamRepository.java
│   ├── Innings_ScoringRepository.java
│   ├── TeamSquadRepository.java
│   ├── StaffRepository.java
│   └── TeamStaffContractRepository.java
├── ui
│   ├── AdminDashboard.java
│   ├── ManagerDashboard.java
│   ├── PlayerDashboard.java
│   └── ViewerDashboard.java
├── utils
│   ├── AppConstants.java
│   └── InputValidator.java
├── Authentication.java
└── Main.java
```
 
---
 
## 🔄 Sample Workflow
 
```
1. Admin creates a Tournament → "IPL 2026"
2. Admin adds Teams and Players
3. Admin creates an Auction linked to the tournament
4. Admin sets each team's starting wallet
5. Admin conducts the auction — players go under the hammer, wallets are deducted
6. Team Manager builds their squad from auctioned players
7. Admin schedules matches and assigns teams
8. Admin starts an innings and records live scores
9. Players & Viewers check auction results, squads, and scorecards
```
 
---
 
## 🔮 Future Improvements
 
- [ ] Migrate from console UI to a REST API / Spring Boot backend
- [ ] Add ball-by-ball live scoring (table already exists: `ball_by_ball`)
- [ ] Password hashing (currently stored as plain text — not production-safe)
- [ ] Move DB credentials to environment variables / `.env` file
- [ ] Add unit tests for repository and entity layers
- [ ] Build a simple web/GUI frontend
---
 
## 👤 Author
 
**Love Kumar**
Cricket Tournament & Auction Management System — built as a hands-on project to learn Java, JDBC, and PostgreSQL through a real-world, relational data model.
 
---
 
⭐ If you found this project interesting, consider giving it a star on GitHub!
 



