--
-- PostgreSQL database dump
--

\restrict TXpSCBheonCANJnObSjHzfZm6cfsCOyvvRxzKQ62YDofhc1uCH7Q26KkmVfJUAh

-- Dumped from database version 18.3
-- Dumped by pg_dump version 18.3

-- Started on 2026-07-02 09:16:14

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 887 (class 1247 OID 17862)
-- Name: auction_type; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.auction_type AS ENUM (
    'mega',
    'mini'
);


ALTER TYPE public.auction_type OWNER TO postgres;

--
-- TOC entry 899 (class 1247 OID 17888)
-- Name: current_status; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.current_status AS ENUM (
    'upcoming',
    'ongoing',
    'completed'
);


ALTER TYPE public.current_status OWNER TO postgres;

--
-- TOC entry 890 (class 1247 OID 17868)
-- Name: match_type; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.match_type AS ENUM (
    't20',
    'odi',
    'test'
);


ALTER TYPE public.match_type OWNER TO postgres;

--
-- TOC entry 896 (class 1247 OID 17882)
-- Name: player_acquisition_type; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.player_acquisition_type AS ENUM (
    'retain',
    'pick'
);


ALTER TYPE public.player_acquisition_type OWNER TO postgres;

--
-- TOC entry 884 (class 1247 OID 17852)
-- Name: player_role; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.player_role AS ENUM (
    'batsman',
    'bowler',
    'all rounder',
    'wicket keeper'
);


ALTER TYPE public.player_role OWNER TO postgres;

--
-- TOC entry 893 (class 1247 OID 17876)
-- Name: player_sold_status; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.player_sold_status AS ENUM (
    'sold',
    'unsold'
);


ALTER TYPE public.player_sold_status OWNER TO postgres;

--
-- TOC entry 881 (class 1247 OID 17844)
-- Name: player_team_role; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.player_team_role AS ENUM (
    'player',
    'captain',
    'vice_captain'
);


ALTER TYPE public.player_team_role OWNER TO postgres;

--
-- TOC entry 902 (class 1247 OID 17896)
-- Name: staff_role; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.staff_role AS ENUM (
    'head_coach',
    'batting_coach',
    'bowling_coach',
    'fielding_coach',
    'team_manager',
    'physio',
    'analyst',
    'trainer'
);


ALTER TYPE public.staff_role OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 224 (class 1259 OID 17935)
-- Name: auction; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.auction (
    auction_id integer NOT NULL,
    auction_name character varying(100),
    auction_year integer,
    auction_type public.auction_type,
    tournament_id integer
);


ALTER TABLE public.auction OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 17934)
-- Name: auction_auction_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.auction_auction_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.auction_auction_id_seq OWNER TO postgres;

--
-- TOC entry 5217 (class 0 OID 0)
-- Dependencies: 223
-- Name: auction_auction_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.auction_auction_id_seq OWNED BY public.auction.auction_id;


--
-- TOC entry 242 (class 1259 OID 18096)
-- Name: ball_by_ball; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ball_by_ball (
    ball_id integer NOT NULL,
    innings_id integer,
    over_number integer,
    ball_number integer,
    batsman_id integer,
    bowler_id integer,
    runs integer,
    extras integer,
    wicket_flag boolean
);


ALTER TABLE public.ball_by_ball OWNER TO postgres;

--
-- TOC entry 241 (class 1259 OID 18095)
-- Name: ball_by_ball_ball_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.ball_by_ball_ball_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.ball_by_ball_ball_id_seq OWNER TO postgres;

--
-- TOC entry 5218 (class 0 OID 0)
-- Dependencies: 241
-- Name: ball_by_ball_ball_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.ball_by_ball_ball_id_seq OWNED BY public.ball_by_ball.ball_id;


--
-- TOC entry 238 (class 1259 OID 18060)
-- Name: innings; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.innings (
    innings_id integer NOT NULL,
    innings_number integer,
    match_id integer,
    batting_team_id integer,
    bowling_team_id integer
);


ALTER TABLE public.innings OWNER TO postgres;

--
-- TOC entry 237 (class 1259 OID 18059)
-- Name: innings_innings_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.innings_innings_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.innings_innings_id_seq OWNER TO postgres;

--
-- TOC entry 5219 (class 0 OID 0)
-- Dependencies: 237
-- Name: innings_innings_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.innings_innings_id_seq OWNED BY public.innings.innings_id;


--
-- TOC entry 240 (class 1259 OID 18083)
-- Name: innings_score; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.innings_score (
    innings_score_id integer NOT NULL,
    innings_id integer,
    total_runs integer,
    total_wickets integer,
    overs_played double precision
);


ALTER TABLE public.innings_score OWNER TO postgres;

--
-- TOC entry 239 (class 1259 OID 18082)
-- Name: innings_score_innings_score_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.innings_score_innings_score_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.innings_score_innings_score_id_seq OWNER TO postgres;

--
-- TOC entry 5220 (class 0 OID 0)
-- Dependencies: 239
-- Name: innings_score_innings_score_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.innings_score_innings_score_id_seq OWNED BY public.innings_score.innings_score_id;


--
-- TOC entry 234 (class 1259 OID 18025)
-- Name: match; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.match (
    match_id integer NOT NULL,
    match_venue character varying(150),
    match_date date,
    match_time time without time zone,
    match_type public.match_type,
    match_overs integer,
    match_status public.current_status,
    tournament_id integer
);


ALTER TABLE public.match OWNER TO postgres;

--
-- TOC entry 233 (class 1259 OID 18024)
-- Name: match_match_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.match_match_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.match_match_id_seq OWNER TO postgres;

--
-- TOC entry 5221 (class 0 OID 0)
-- Dependencies: 233
-- Name: match_match_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.match_match_id_seq OWNED BY public.match.match_id;


--
-- TOC entry 236 (class 1259 OID 18040)
-- Name: match_team; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.match_team (
    match_team_id integer NOT NULL,
    match_id integer,
    team_id integer
);


ALTER TABLE public.match_team OWNER TO postgres;

--
-- TOC entry 235 (class 1259 OID 18039)
-- Name: match_team_match_team_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.match_team_match_team_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.match_team_match_team_id_seq OWNER TO postgres;

--
-- TOC entry 5222 (class 0 OID 0)
-- Dependencies: 235
-- Name: match_team_match_team_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.match_team_match_team_id_seq OWNED BY public.match_team.match_team_id;


--
-- TOC entry 226 (class 1259 OID 17948)
-- Name: player; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.player (
    player_id integer NOT NULL,
    player_name character varying(80),
    player_country character varying(100),
    player_age integer,
    player_role public.player_role,
    user_id integer
);


ALTER TABLE public.player OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 17947)
-- Name: player_player_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.player_player_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.player_player_id_seq OWNER TO postgres;

--
-- TOC entry 5223 (class 0 OID 0)
-- Dependencies: 225
-- Name: player_player_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.player_player_id_seq OWNED BY public.player.player_id;


--
-- TOC entry 230 (class 1259 OID 17975)
-- Name: player_team_auction; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.player_team_auction (
    player_team_auction_id integer NOT NULL,
    base_price double precision,
    sold_price double precision,
    sold_status public.player_sold_status,
    team_auction_year integer,
    acquisition_type public.player_acquisition_type,
    team_role public.player_team_role,
    player_id integer,
    team_id integer,
    auction_id integer,
    CONSTRAINT player_team_auction_base_price_check CHECK ((base_price >= (0)::double precision)),
    CONSTRAINT player_team_auction_sold_price_check CHECK ((sold_price >= (0)::double precision))
);


ALTER TABLE public.player_team_auction OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 17974)
-- Name: player_team_auction_player_team_auction_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.player_team_auction_player_team_auction_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.player_team_auction_player_team_auction_id_seq OWNER TO postgres;

--
-- TOC entry 5224 (class 0 OID 0)
-- Dependencies: 229
-- Name: player_team_auction_player_team_auction_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.player_team_auction_player_team_auction_id_seq OWNED BY public.player_team_auction.player_team_auction_id;


--
-- TOC entry 244 (class 1259 OID 18119)
-- Name: staff; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.staff (
    staff_id integer NOT NULL,
    staff_name character varying(60),
    staff_country character varying(100),
    experience integer,
    staff_role public.staff_role
);


ALTER TABLE public.staff OWNER TO postgres;

--
-- TOC entry 243 (class 1259 OID 18118)
-- Name: staff_staff_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.staff_staff_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.staff_staff_id_seq OWNER TO postgres;

--
-- TOC entry 5225 (class 0 OID 0)
-- Dependencies: 243
-- Name: staff_staff_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.staff_staff_id_seq OWNED BY public.staff.staff_id;


--
-- TOC entry 220 (class 1259 OID 17914)
-- Name: team; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.team (
    team_id integer NOT NULL,
    team_name character varying(100),
    team_city character varying(100),
    team_owner character varying(70),
    manager_user_id integer
);


ALTER TABLE public.team OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 17956)
-- Name: team_auction; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.team_auction (
    team_auction_id integer NOT NULL,
    team_total_wallet double precision,
    team_id integer,
    auction_id integer,
    CONSTRAINT team_auction_team_total_wallet_check CHECK ((team_total_wallet >= (0)::double precision))
);


ALTER TABLE public.team_auction OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 17955)
-- Name: team_auction_team_auction_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.team_auction_team_auction_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.team_auction_team_auction_id_seq OWNER TO postgres;

--
-- TOC entry 5226 (class 0 OID 0)
-- Dependencies: 227
-- Name: team_auction_team_auction_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.team_auction_team_auction_id_seq OWNED BY public.team_auction.team_auction_id;


--
-- TOC entry 232 (class 1259 OID 18000)
-- Name: team_squad; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.team_squad (
    squad_id integer NOT NULL,
    team_id integer,
    player_id integer,
    tournament_id integer,
    team_role public.player_team_role
);


ALTER TABLE public.team_squad OWNER TO postgres;

--
-- TOC entry 231 (class 1259 OID 17999)
-- Name: team_squad_squad_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.team_squad_squad_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.team_squad_squad_id_seq OWNER TO postgres;

--
-- TOC entry 5227 (class 0 OID 0)
-- Dependencies: 231
-- Name: team_squad_squad_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.team_squad_squad_id_seq OWNED BY public.team_squad.squad_id;


--
-- TOC entry 246 (class 1259 OID 18127)
-- Name: team_staff_contract; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.team_staff_contract (
    contract_id integer NOT NULL,
    team_id integer,
    staff_id integer,
    role public.staff_role,
    duration_from date,
    duration_till date
);


ALTER TABLE public.team_staff_contract OWNER TO postgres;

--
-- TOC entry 245 (class 1259 OID 18126)
-- Name: team_staff_contract_contract_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.team_staff_contract_contract_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.team_staff_contract_contract_id_seq OWNER TO postgres;

--
-- TOC entry 5228 (class 0 OID 0)
-- Dependencies: 245
-- Name: team_staff_contract_contract_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.team_staff_contract_contract_id_seq OWNED BY public.team_staff_contract.contract_id;


--
-- TOC entry 219 (class 1259 OID 17913)
-- Name: team_team_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.team_team_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.team_team_id_seq OWNER TO postgres;

--
-- TOC entry 5229 (class 0 OID 0)
-- Dependencies: 219
-- Name: team_team_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.team_team_id_seq OWNED BY public.team.team_id;


--
-- TOC entry 222 (class 1259 OID 17922)
-- Name: tournament; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tournament (
    tournament_id integer NOT NULL,
    tournament_name character varying(150),
    tournament_year integer,
    tournament_country character varying(100),
    tournament_status public.current_status,
    tournament_winner_team_id integer
);


ALTER TABLE public.tournament OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 17921)
-- Name: tournament_tournament_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tournament_tournament_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.tournament_tournament_id_seq OWNER TO postgres;

--
-- TOC entry 5230 (class 0 OID 0)
-- Dependencies: 221
-- Name: tournament_tournament_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tournament_tournament_id_seq OWNED BY public.tournament.tournament_id;


--
-- TOC entry 248 (class 1259 OID 18145)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    user_id integer NOT NULL,
    user_name character varying(255) NOT NULL,
    user_email character varying(233) NOT NULL,
    user_password character varying(122) NOT NULL,
    user_role character varying(100) NOT NULL,
    created_at timestamp without time zone DEFAULT now()
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 247 (class 1259 OID 18144)
-- Name: users_user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.users_user_id_seq OWNER TO postgres;

--
-- TOC entry 5231 (class 0 OID 0)
-- Dependencies: 247
-- Name: users_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_user_id_seq OWNED BY public.users.user_id;


--
-- TOC entry 4952 (class 2604 OID 17938)
-- Name: auction auction_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.auction ALTER COLUMN auction_id SET DEFAULT nextval('public.auction_auction_id_seq'::regclass);


--
-- TOC entry 4961 (class 2604 OID 18099)
-- Name: ball_by_ball ball_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ball_by_ball ALTER COLUMN ball_id SET DEFAULT nextval('public.ball_by_ball_ball_id_seq'::regclass);


--
-- TOC entry 4959 (class 2604 OID 18063)
-- Name: innings innings_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.innings ALTER COLUMN innings_id SET DEFAULT nextval('public.innings_innings_id_seq'::regclass);


--
-- TOC entry 4960 (class 2604 OID 18086)
-- Name: innings_score innings_score_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.innings_score ALTER COLUMN innings_score_id SET DEFAULT nextval('public.innings_score_innings_score_id_seq'::regclass);


--
-- TOC entry 4957 (class 2604 OID 18028)
-- Name: match match_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.match ALTER COLUMN match_id SET DEFAULT nextval('public.match_match_id_seq'::regclass);


--
-- TOC entry 4958 (class 2604 OID 18043)
-- Name: match_team match_team_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.match_team ALTER COLUMN match_team_id SET DEFAULT nextval('public.match_team_match_team_id_seq'::regclass);


--
-- TOC entry 4953 (class 2604 OID 17951)
-- Name: player player_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.player ALTER COLUMN player_id SET DEFAULT nextval('public.player_player_id_seq'::regclass);


--
-- TOC entry 4955 (class 2604 OID 17978)
-- Name: player_team_auction player_team_auction_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.player_team_auction ALTER COLUMN player_team_auction_id SET DEFAULT nextval('public.player_team_auction_player_team_auction_id_seq'::regclass);


--
-- TOC entry 4962 (class 2604 OID 18122)
-- Name: staff staff_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.staff ALTER COLUMN staff_id SET DEFAULT nextval('public.staff_staff_id_seq'::regclass);


--
-- TOC entry 4950 (class 2604 OID 17917)
-- Name: team team_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.team ALTER COLUMN team_id SET DEFAULT nextval('public.team_team_id_seq'::regclass);


--
-- TOC entry 4954 (class 2604 OID 17959)
-- Name: team_auction team_auction_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.team_auction ALTER COLUMN team_auction_id SET DEFAULT nextval('public.team_auction_team_auction_id_seq'::regclass);


--
-- TOC entry 4956 (class 2604 OID 18003)
-- Name: team_squad squad_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.team_squad ALTER COLUMN squad_id SET DEFAULT nextval('public.team_squad_squad_id_seq'::regclass);


--
-- TOC entry 4963 (class 2604 OID 18130)
-- Name: team_staff_contract contract_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.team_staff_contract ALTER COLUMN contract_id SET DEFAULT nextval('public.team_staff_contract_contract_id_seq'::regclass);


--
-- TOC entry 4951 (class 2604 OID 17925)
-- Name: tournament tournament_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tournament ALTER COLUMN tournament_id SET DEFAULT nextval('public.tournament_tournament_id_seq'::regclass);


--
-- TOC entry 4964 (class 2604 OID 18148)
-- Name: users user_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN user_id SET DEFAULT nextval('public.users_user_id_seq'::regclass);


--
-- TOC entry 5187 (class 0 OID 17935)
-- Dependencies: 224
-- Data for Name: auction; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.auction VALUES (1, 'Auction1', 2020, 'mega', 1);
INSERT INTO public.auction VALUES (2, 'Auction2', 2021, 'mini', 2);
INSERT INTO public.auction VALUES (3, 'Auction3', 2022, 'mega', 3);
INSERT INTO public.auction VALUES (4, 'Auction4', 2023, 'mini', 4);
INSERT INTO public.auction VALUES (5, 'Auction5', 2024, 'mega', 5);
INSERT INTO public.auction VALUES (6, 'Auction6', 2025, 'mini', 6);
INSERT INTO public.auction VALUES (7, 'Auction7', 2026, 'mega', 7);
INSERT INTO public.auction VALUES (8, 'Auction8', 2024, 'mini', 8);
INSERT INTO public.auction VALUES (9, 'Auction9', 2025, 'mega', 9);
INSERT INTO public.auction VALUES (10, 'Auction10', 2026, 'mini', 10);


--
-- TOC entry 5205 (class 0 OID 18096)
-- Dependencies: 242
-- Data for Name: ball_by_ball; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.ball_by_ball VALUES (1, 1, 1, 1, 1, 2, 1, 0, false);
INSERT INTO public.ball_by_ball VALUES (2, 1, 1, 2, 1, 2, 4, 0, false);
INSERT INTO public.ball_by_ball VALUES (3, 2, 1, 1, 2, 1, 0, 0, true);
INSERT INTO public.ball_by_ball VALUES (4, 3, 1, 1, 3, 4, 2, 0, false);
INSERT INTO public.ball_by_ball VALUES (5, 4, 1, 1, 4, 3, 6, 0, false);
INSERT INTO public.ball_by_ball VALUES (6, 5, 1, 1, 5, 6, 1, 0, false);
INSERT INTO public.ball_by_ball VALUES (7, 6, 1, 1, 6, 5, 3, 0, false);
INSERT INTO public.ball_by_ball VALUES (8, 7, 1, 1, 7, 8, 0, 1, false);
INSERT INTO public.ball_by_ball VALUES (9, 8, 1, 1, 8, 7, 4, 0, false);
INSERT INTO public.ball_by_ball VALUES (10, 9, 1, 1, 9, 10, 2, 0, false);


--
-- TOC entry 5201 (class 0 OID 18060)
-- Dependencies: 238
-- Data for Name: innings; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.innings VALUES (1, 1, 1, 1, 2);
INSERT INTO public.innings VALUES (2, 2, 1, 2, 1);
INSERT INTO public.innings VALUES (3, 1, 2, 3, 4);
INSERT INTO public.innings VALUES (4, 2, 2, 4, 3);
INSERT INTO public.innings VALUES (5, 1, 3, 5, 6);
INSERT INTO public.innings VALUES (6, 2, 3, 6, 5);
INSERT INTO public.innings VALUES (7, 1, 4, 7, 8);
INSERT INTO public.innings VALUES (8, 2, 4, 8, 7);
INSERT INTO public.innings VALUES (9, 1, 5, 9, 10);
INSERT INTO public.innings VALUES (10, 2, 5, 10, 9);


--
-- TOC entry 5203 (class 0 OID 18083)
-- Dependencies: 240
-- Data for Name: innings_score; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.innings_score VALUES (1, 1, 150, 5, 20);
INSERT INTO public.innings_score VALUES (2, 2, 151, 4, 19.5);
INSERT INTO public.innings_score VALUES (3, 3, 160, 6, 20);
INSERT INTO public.innings_score VALUES (4, 4, 140, 8, 20);
INSERT INTO public.innings_score VALUES (5, 5, 170, 3, 20);
INSERT INTO public.innings_score VALUES (6, 6, 171, 2, 19.2);
INSERT INTO public.innings_score VALUES (7, 7, 180, 5, 20);
INSERT INTO public.innings_score VALUES (8, 8, 181, 4, 19.4);
INSERT INTO public.innings_score VALUES (9, 9, 130, 9, 20);
INSERT INTO public.innings_score VALUES (10, 10, 131, 8, 18.5);


--
-- TOC entry 5197 (class 0 OID 18025)
-- Dependencies: 234
-- Data for Name: match; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.match VALUES (1, 'Venue1', '2025-01-01', '10:00:00', 't20', 20, 'completed', 1);
INSERT INTO public.match VALUES (2, 'Venue2', '2025-01-02', '10:00:00', 't20', 20, 'completed', 2);
INSERT INTO public.match VALUES (3, 'Venue3', '2025-01-03', '10:00:00', 't20', 20, 'completed', 3);
INSERT INTO public.match VALUES (4, 'Venue4', '2025-01-04', '10:00:00', 't20', 20, 'completed', 4);
INSERT INTO public.match VALUES (5, 'Venue5', '2025-01-05', '10:00:00', 't20', 20, 'completed', 5);
INSERT INTO public.match VALUES (6, 'Venue6', '2025-01-06', '10:00:00', 't20', 20, 'completed', 6);
INSERT INTO public.match VALUES (7, 'Venue7', '2025-01-07', '10:00:00', 't20', 20, 'ongoing', 7);
INSERT INTO public.match VALUES (8, 'Venue8', '2025-01-08', '10:00:00', 'odi', 50, 'ongoing', 8);
INSERT INTO public.match VALUES (9, 'Venue9', '2025-01-09', '10:00:00', 'test', 90, 'upcoming', 9);
INSERT INTO public.match VALUES (10, 'Venue10', '2025-01-10', '10:00:00', 't20', 20, 'upcoming', 10);
INSERT INTO public.match VALUES (11, 'Samaro Road pk', '2026-09-25', '08:00:00', 't20', 20, 'upcoming', 10);


--
-- TOC entry 5199 (class 0 OID 18040)
-- Dependencies: 236
-- Data for Name: match_team; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.match_team VALUES (1, 1, 1);
INSERT INTO public.match_team VALUES (2, 1, 2);
INSERT INTO public.match_team VALUES (3, 2, 3);
INSERT INTO public.match_team VALUES (4, 2, 4);
INSERT INTO public.match_team VALUES (5, 3, 5);
INSERT INTO public.match_team VALUES (6, 3, 6);
INSERT INTO public.match_team VALUES (7, 4, 7);
INSERT INTO public.match_team VALUES (8, 4, 8);
INSERT INTO public.match_team VALUES (9, 5, 9);
INSERT INTO public.match_team VALUES (10, 5, 10);
INSERT INTO public.match_team VALUES (11, 2, 2);


--
-- TOC entry 5189 (class 0 OID 17948)
-- Dependencies: 226
-- Data for Name: player; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.player VALUES (1, 'Player1', 'Pakistan', 21, 'batsman', NULL);
INSERT INTO public.player VALUES (2, 'Player2', 'Pakistan', 22, 'bowler', NULL);
INSERT INTO public.player VALUES (3, 'Player3', 'Pakistan', 23, 'all rounder', NULL);
INSERT INTO public.player VALUES (4, 'Player4', 'Pakistan', 24, 'wicket keeper', NULL);
INSERT INTO public.player VALUES (5, 'Player5', 'Pakistan', 25, 'batsman', NULL);
INSERT INTO public.player VALUES (6, 'Player6', 'Pakistan', 26, 'bowler', NULL);
INSERT INTO public.player VALUES (7, 'Player7', 'Pakistan', 27, 'all rounder', NULL);
INSERT INTO public.player VALUES (8, 'Player8', 'Pakistan', 28, 'wicket keeper', NULL);
INSERT INTO public.player VALUES (9, 'Player9', 'Pakistan', 29, 'batsman', NULL);
INSERT INTO public.player VALUES (10, 'Player10', 'Pakistan', 30, 'bowler', NULL);


--
-- TOC entry 5193 (class 0 OID 17975)
-- Dependencies: 230
-- Data for Name: player_team_auction; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.player_team_auction VALUES (1, 100, 500, 'sold', 2020, 'pick', 'captain', 1, 1, 1);
INSERT INTO public.player_team_auction VALUES (2, 100, 500, 'sold', 2021, 'pick', 'player', 2, 2, 2);
INSERT INTO public.player_team_auction VALUES (3, 100, 500, 'sold', 2022, 'pick', 'player', 3, 3, 3);
INSERT INTO public.player_team_auction VALUES (4, 100, 500, 'sold', 2023, 'pick', 'player', 4, 4, 4);
INSERT INTO public.player_team_auction VALUES (5, 100, 500, 'sold', 2024, 'pick', 'player', 5, 5, 5);
INSERT INTO public.player_team_auction VALUES (6, 100, 500, 'sold', 2025, 'pick', 'player', 6, 6, 6);
INSERT INTO public.player_team_auction VALUES (7, 100, 500, 'sold', 2026, 'pick', 'player', 7, 7, 7);
INSERT INTO public.player_team_auction VALUES (8, 100, 500, 'sold', 2024, 'pick', 'player', 8, 8, 8);
INSERT INTO public.player_team_auction VALUES (9, 100, 500, 'sold', 2025, 'pick', 'vice_captain', 9, 9, 9);
INSERT INTO public.player_team_auction VALUES (10, 100, 500, 'sold', 2026, 'pick', 'captain', 10, 10, 10);
INSERT INTO public.player_team_auction VALUES (11, 1000, 2000, 'sold', NULL, 'pick', 'player', 1, 3, 3);


--
-- TOC entry 5207 (class 0 OID 18119)
-- Dependencies: 244
-- Data for Name: staff; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.staff VALUES (1, 'Staff1', 'Pakistan', 5, 'head_coach');
INSERT INTO public.staff VALUES (2, 'Staff2', 'Pakistan', 5, 'batting_coach');
INSERT INTO public.staff VALUES (3, 'Staff3', 'Pakistan', 5, 'bowling_coach');
INSERT INTO public.staff VALUES (4, 'Staff4', 'Pakistan', 5, 'fielding_coach');
INSERT INTO public.staff VALUES (5, 'Staff5', 'Pakistan', 5, 'team_manager');
INSERT INTO public.staff VALUES (6, 'Staff6', 'Pakistan', 5, 'physio');
INSERT INTO public.staff VALUES (7, 'Staff7', 'Pakistan', 5, 'analyst');
INSERT INTO public.staff VALUES (8, 'Staff8', 'Pakistan', 5, 'trainer');
INSERT INTO public.staff VALUES (9, 'Staff9', 'Pakistan', 5, 'head_coach');
INSERT INTO public.staff VALUES (10, 'Staff10', 'Pakistan', 5, 'analyst');


--
-- TOC entry 5183 (class 0 OID 17914)
-- Dependencies: 220
-- Data for Name: team; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.team VALUES (1, 'Karachi Kings', 'Karachi', 'Salman Iqbal', NULL);
INSERT INTO public.team VALUES (2, 'Lahore Qalandars', 'Lahore', 'Fawad Rana', NULL);
INSERT INTO public.team VALUES (3, 'Islamabad United', 'Islamabad', 'Ali Naqvi', NULL);
INSERT INTO public.team VALUES (4, 'Peshawar Zalmi', 'Peshawar', 'Javed Afridi', NULL);
INSERT INTO public.team VALUES (5, 'Quetta Gladiators', 'Quetta', 'Nadeem Omar', NULL);
INSERT INTO public.team VALUES (6, 'Multan Sultans', 'Multan', 'Owner6', NULL);
INSERT INTO public.team VALUES (7, 'Sialkot Stars', 'Sialkot', 'Owner7', NULL);
INSERT INTO public.team VALUES (8, 'Faisalabad Falcons', 'Faisalabad', 'Owner8', NULL);
INSERT INTO public.team VALUES (9, 'Hyderabad Hawks', 'Hyderabad', 'Owner9', NULL);
INSERT INTO public.team VALUES (10, 'Rawalpindi Raiders', 'Rawalpindi', 'Owner10', NULL);


--
-- TOC entry 5191 (class 0 OID 17956)
-- Dependencies: 228
-- Data for Name: team_auction; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.team_auction VALUES (1, 1000000, 1, 1);
INSERT INTO public.team_auction VALUES (2, 1000000, 2, 2);
INSERT INTO public.team_auction VALUES (4, 1000000, 4, 4);
INSERT INTO public.team_auction VALUES (5, 1000000, 5, 5);
INSERT INTO public.team_auction VALUES (6, 1000000, 6, 6);
INSERT INTO public.team_auction VALUES (7, 1000000, 7, 7);
INSERT INTO public.team_auction VALUES (8, 1000000, 8, 8);
INSERT INTO public.team_auction VALUES (9, 1000000, 9, 9);
INSERT INTO public.team_auction VALUES (10, 1000000, 10, 10);
INSERT INTO public.team_auction VALUES (3, 998000, 3, 3);


--
-- TOC entry 5195 (class 0 OID 18000)
-- Dependencies: 232
-- Data for Name: team_squad; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.team_squad VALUES (1, 1, 1, 1, 'captain');
INSERT INTO public.team_squad VALUES (2, 2, 2, 2, 'player');
INSERT INTO public.team_squad VALUES (3, 3, 3, 3, 'player');
INSERT INTO public.team_squad VALUES (4, 4, 4, 4, 'player');
INSERT INTO public.team_squad VALUES (5, 5, 5, 5, 'player');
INSERT INTO public.team_squad VALUES (6, 6, 6, 6, 'player');
INSERT INTO public.team_squad VALUES (7, 7, 7, 7, 'player');
INSERT INTO public.team_squad VALUES (8, 8, 8, 8, 'player');
INSERT INTO public.team_squad VALUES (9, 9, 9, 9, 'vice_captain');
INSERT INTO public.team_squad VALUES (10, 10, 10, 10, 'captain');


--
-- TOC entry 5209 (class 0 OID 18127)
-- Dependencies: 246
-- Data for Name: team_staff_contract; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.team_staff_contract VALUES (1, 1, 1, 'head_coach', '2025-01-01', '2025-12-31');
INSERT INTO public.team_staff_contract VALUES (2, 2, 2, 'batting_coach', '2025-01-01', '2025-12-31');
INSERT INTO public.team_staff_contract VALUES (3, 3, 3, 'bowling_coach', '2025-01-01', '2025-12-31');
INSERT INTO public.team_staff_contract VALUES (4, 4, 4, 'fielding_coach', '2025-01-01', '2025-12-31');
INSERT INTO public.team_staff_contract VALUES (5, 5, 5, 'team_manager', '2025-01-01', '2025-12-31');
INSERT INTO public.team_staff_contract VALUES (6, 6, 6, 'physio', '2025-01-01', '2025-12-31');
INSERT INTO public.team_staff_contract VALUES (7, 7, 7, 'analyst', '2025-01-01', '2025-12-31');
INSERT INTO public.team_staff_contract VALUES (8, 8, 8, 'trainer', '2025-01-01', '2025-12-31');
INSERT INTO public.team_staff_contract VALUES (9, 9, 9, 'head_coach', '2025-01-01', '2025-12-31');
INSERT INTO public.team_staff_contract VALUES (10, 10, 10, 'analyst', '2025-01-01', '2025-12-31');


--
-- TOC entry 5185 (class 0 OID 17922)
-- Dependencies: 222
-- Data for Name: tournament; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.tournament VALUES (1, 'PSL', 2020, 'Pakistan', 'completed', 1);
INSERT INTO public.tournament VALUES (2, 'PSL', 2021, 'Pakistan', 'completed', 2);
INSERT INTO public.tournament VALUES (3, 'PSL', 2022, 'Pakistan', 'completed', 3);
INSERT INTO public.tournament VALUES (4, 'PSL', 2023, 'Pakistan', 'completed', 4);
INSERT INTO public.tournament VALUES (5, 'PSL', 2024, 'Pakistan', 'completed', 5);
INSERT INTO public.tournament VALUES (6, 'PSL', 2025, 'Pakistan', 'completed', 6);
INSERT INTO public.tournament VALUES (7, 'PSL', 2026, 'Pakistan', 'upcoming', NULL);
INSERT INTO public.tournament VALUES (8, 'Champions Cup', 2024, 'Pakistan', 'completed', 7);
INSERT INTO public.tournament VALUES (9, 'National T20', 2025, 'Pakistan', 'ongoing', NULL);
INSERT INTO public.tournament VALUES (10, 'Super League', 2026, 'Pakistan', 'upcoming', NULL);
INSERT INTO public.tournament VALUES (11, '100''s of England', 2027, 'England', 'upcoming', NULL);
INSERT INTO public.tournament VALUES (12, 'MPL-#Diwali#', 2026, 'Pakistan', 'upcoming', NULL);


--
-- TOC entry 5211 (class 0 OID 18145)
-- Dependencies: 248
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.users VALUES (1, 'love', 'lk@google.com', '5432`', 'Admin', '2026-06-23 11:48:46.820485');
INSERT INTO public.users VALUES (2, 'Lovish kumar', 'lovish@kmail.com', 'asdf', 'Team manager', '2026-06-23 17:52:13.475241');
INSERT INTO public.users VALUES (3, 'Falak Pardeep', 'falak@hotmail.com', 'qwer', 'Player', '2026-06-23 17:55:50.009159');
INSERT INTO public.users VALUES (4, 'Love kumar', 'lkmalhi99@gmail.com', 'Strong0098', 'Admin', '2026-06-23 18:02:23.685731');
INSERT INTO public.users VALUES (5, 'Harry Brook', 'harry@englans.com', 'englans', 'Player', '2026-07-02 08:55:53.473092');


--
-- TOC entry 5232 (class 0 OID 0)
-- Dependencies: 223
-- Name: auction_auction_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.auction_auction_id_seq', 10, true);


--
-- TOC entry 5233 (class 0 OID 0)
-- Dependencies: 241
-- Name: ball_by_ball_ball_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.ball_by_ball_ball_id_seq', 10, true);


--
-- TOC entry 5234 (class 0 OID 0)
-- Dependencies: 237
-- Name: innings_innings_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.innings_innings_id_seq', 10, true);


--
-- TOC entry 5235 (class 0 OID 0)
-- Dependencies: 239
-- Name: innings_score_innings_score_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.innings_score_innings_score_id_seq', 10, true);


--
-- TOC entry 5236 (class 0 OID 0)
-- Dependencies: 233
-- Name: match_match_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.match_match_id_seq', 11, true);


--
-- TOC entry 5237 (class 0 OID 0)
-- Dependencies: 235
-- Name: match_team_match_team_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.match_team_match_team_id_seq', 11, true);


--
-- TOC entry 5238 (class 0 OID 0)
-- Dependencies: 225
-- Name: player_player_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.player_player_id_seq', 10, true);


--
-- TOC entry 5239 (class 0 OID 0)
-- Dependencies: 229
-- Name: player_team_auction_player_team_auction_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.player_team_auction_player_team_auction_id_seq', 11, true);


--
-- TOC entry 5240 (class 0 OID 0)
-- Dependencies: 243
-- Name: staff_staff_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.staff_staff_id_seq', 10, true);


--
-- TOC entry 5241 (class 0 OID 0)
-- Dependencies: 227
-- Name: team_auction_team_auction_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.team_auction_team_auction_id_seq', 10, true);


--
-- TOC entry 5242 (class 0 OID 0)
-- Dependencies: 231
-- Name: team_squad_squad_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.team_squad_squad_id_seq', 12, true);


--
-- TOC entry 5243 (class 0 OID 0)
-- Dependencies: 245
-- Name: team_staff_contract_contract_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.team_staff_contract_contract_id_seq', 10, true);


--
-- TOC entry 5244 (class 0 OID 0)
-- Dependencies: 219
-- Name: team_team_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.team_team_id_seq', 10, true);


--
-- TOC entry 5245 (class 0 OID 0)
-- Dependencies: 221
-- Name: tournament_tournament_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tournament_tournament_id_seq', 12, true);


--
-- TOC entry 5246 (class 0 OID 0)
-- Dependencies: 247
-- Name: users_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_user_id_seq', 5, true);


--
-- TOC entry 4976 (class 2606 OID 17941)
-- Name: auction auction_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.auction
    ADD CONSTRAINT auction_pkey PRIMARY KEY (auction_id);


--
-- TOC entry 5002 (class 2606 OID 18102)
-- Name: ball_by_ball ball_by_ball_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ball_by_ball
    ADD CONSTRAINT ball_by_ball_pkey PRIMARY KEY (ball_id);


--
-- TOC entry 4998 (class 2606 OID 18066)
-- Name: innings innings_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.innings
    ADD CONSTRAINT innings_pkey PRIMARY KEY (innings_id);


--
-- TOC entry 5000 (class 2606 OID 18089)
-- Name: innings_score innings_score_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.innings_score
    ADD CONSTRAINT innings_score_pkey PRIMARY KEY (innings_score_id);


--
-- TOC entry 4990 (class 2606 OID 18033)
-- Name: match match_match_venue_match_date_match_time_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.match
    ADD CONSTRAINT match_match_venue_match_date_match_time_key UNIQUE (match_venue, match_date, match_time);


--
-- TOC entry 4992 (class 2606 OID 18031)
-- Name: match match_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.match
    ADD CONSTRAINT match_pkey PRIMARY KEY (match_id);


--
-- TOC entry 4994 (class 2606 OID 18048)
-- Name: match_team match_team_match_id_team_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.match_team
    ADD CONSTRAINT match_team_match_id_team_id_key UNIQUE (match_id, team_id);


--
-- TOC entry 4996 (class 2606 OID 18046)
-- Name: match_team match_team_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.match_team
    ADD CONSTRAINT match_team_pkey PRIMARY KEY (match_team_id);


--
-- TOC entry 4978 (class 2606 OID 17954)
-- Name: player player_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.player
    ADD CONSTRAINT player_pkey PRIMARY KEY (player_id);


--
-- TOC entry 4984 (class 2606 OID 17983)
-- Name: player_team_auction player_team_auction_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.player_team_auction
    ADD CONSTRAINT player_team_auction_pkey PRIMARY KEY (player_team_auction_id);


--
-- TOC entry 4980 (class 2606 OID 18164)
-- Name: player player_user_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.player
    ADD CONSTRAINT player_user_id_key UNIQUE (user_id);


--
-- TOC entry 5004 (class 2606 OID 18125)
-- Name: staff staff_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.staff
    ADD CONSTRAINT staff_pkey PRIMARY KEY (staff_id);


--
-- TOC entry 4982 (class 2606 OID 17963)
-- Name: team_auction team_auction_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.team_auction
    ADD CONSTRAINT team_auction_pkey PRIMARY KEY (team_auction_id);


--
-- TOC entry 4970 (class 2606 OID 18171)
-- Name: team team_manager_user_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.team
    ADD CONSTRAINT team_manager_user_id_key UNIQUE (manager_user_id);


--
-- TOC entry 4972 (class 2606 OID 17920)
-- Name: team team_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.team
    ADD CONSTRAINT team_pkey PRIMARY KEY (team_id);


--
-- TOC entry 4986 (class 2606 OID 18006)
-- Name: team_squad team_squad_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.team_squad
    ADD CONSTRAINT team_squad_pkey PRIMARY KEY (squad_id);


--
-- TOC entry 4988 (class 2606 OID 18008)
-- Name: team_squad team_squad_player_id_tournament_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.team_squad
    ADD CONSTRAINT team_squad_player_id_tournament_id_key UNIQUE (player_id, tournament_id);


--
-- TOC entry 5006 (class 2606 OID 18133)
-- Name: team_staff_contract team_staff_contract_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.team_staff_contract
    ADD CONSTRAINT team_staff_contract_pkey PRIMARY KEY (contract_id);


--
-- TOC entry 4974 (class 2606 OID 17928)
-- Name: tournament tournament_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tournament
    ADD CONSTRAINT tournament_pkey PRIMARY KEY (tournament_id);


--
-- TOC entry 5008 (class 2606 OID 18158)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (user_id);


--
-- TOC entry 5010 (class 2606 OID 18160)
-- Name: users users_user_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_user_email_key UNIQUE (user_email);


--
-- TOC entry 5030 (class 2606 OID 18108)
-- Name: ball_by_ball fk_ball_batsman; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ball_by_ball
    ADD CONSTRAINT fk_ball_batsman FOREIGN KEY (batsman_id) REFERENCES public.player(player_id);


--
-- TOC entry 5031 (class 2606 OID 18113)
-- Name: ball_by_ball fk_ball_bowler; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ball_by_ball
    ADD CONSTRAINT fk_ball_bowler FOREIGN KEY (bowler_id) REFERENCES public.player(player_id);


--
-- TOC entry 5032 (class 2606 OID 18103)
-- Name: ball_by_ball fk_ball_innings; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ball_by_ball
    ADD CONSTRAINT fk_ball_innings FOREIGN KEY (innings_id) REFERENCES public.innings(innings_id);


--
-- TOC entry 5026 (class 2606 OID 18072)
-- Name: innings fk_innings_batting_team; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.innings
    ADD CONSTRAINT fk_innings_batting_team FOREIGN KEY (batting_team_id) REFERENCES public.team(team_id);


--
-- TOC entry 5027 (class 2606 OID 18077)
-- Name: innings fk_innings_bowling_team; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.innings
    ADD CONSTRAINT fk_innings_bowling_team FOREIGN KEY (bowling_team_id) REFERENCES public.team(team_id);


--
-- TOC entry 5028 (class 2606 OID 18067)
-- Name: innings fk_innings_match; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.innings
    ADD CONSTRAINT fk_innings_match FOREIGN KEY (match_id) REFERENCES public.match(match_id);


--
-- TOC entry 5029 (class 2606 OID 18090)
-- Name: innings_score fk_innings_score_innings; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.innings_score
    ADD CONSTRAINT fk_innings_score_innings FOREIGN KEY (innings_id) REFERENCES public.innings(innings_id);


--
-- TOC entry 5023 (class 2606 OID 18034)
-- Name: match fk_match_tournament; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.match
    ADD CONSTRAINT fk_match_tournament FOREIGN KEY (tournament_id) REFERENCES public.tournament(tournament_id);


--
-- TOC entry 5024 (class 2606 OID 18049)
-- Name: match_team fk_mt_match; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.match_team
    ADD CONSTRAINT fk_mt_match FOREIGN KEY (match_id) REFERENCES public.match(match_id);


--
-- TOC entry 5025 (class 2606 OID 18054)
-- Name: match_team fk_mt_team; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.match_team
    ADD CONSTRAINT fk_mt_team FOREIGN KEY (team_id) REFERENCES public.team(team_id);


--
-- TOC entry 5017 (class 2606 OID 17994)
-- Name: player_team_auction fk_ptea_auction; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.player_team_auction
    ADD CONSTRAINT fk_ptea_auction FOREIGN KEY (auction_id) REFERENCES public.auction(auction_id);


--
-- TOC entry 5018 (class 2606 OID 17984)
-- Name: player_team_auction fk_ptea_player; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.player_team_auction
    ADD CONSTRAINT fk_ptea_player FOREIGN KEY (player_id) REFERENCES public.player(player_id);


--
-- TOC entry 5019 (class 2606 OID 17989)
-- Name: player_team_auction fk_ptea_team; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.player_team_auction
    ADD CONSTRAINT fk_ptea_team FOREIGN KEY (team_id) REFERENCES public.team(team_id);


--
-- TOC entry 5015 (class 2606 OID 17969)
-- Name: team_auction fk_team_auction_auction; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.team_auction
    ADD CONSTRAINT fk_team_auction_auction FOREIGN KEY (auction_id) REFERENCES public.auction(auction_id);


--
-- TOC entry 5016 (class 2606 OID 17964)
-- Name: team_auction fk_team_auction_team; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.team_auction
    ADD CONSTRAINT fk_team_auction_team FOREIGN KEY (team_id) REFERENCES public.team(team_id);


--
-- TOC entry 5033 (class 2606 OID 18139)
-- Name: team_staff_contract fk_team_staff_contract_staff; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.team_staff_contract
    ADD CONSTRAINT fk_team_staff_contract_staff FOREIGN KEY (staff_id) REFERENCES public.staff(staff_id);


--
-- TOC entry 5034 (class 2606 OID 18134)
-- Name: team_staff_contract fk_team_staff_contract_team; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.team_staff_contract
    ADD CONSTRAINT fk_team_staff_contract_team FOREIGN KEY (team_id) REFERENCES public.team(team_id);


--
-- TOC entry 5013 (class 2606 OID 17942)
-- Name: auction fk_tournament_auction; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.auction
    ADD CONSTRAINT fk_tournament_auction FOREIGN KEY (tournament_id) REFERENCES public.tournament(tournament_id);


--
-- TOC entry 5012 (class 2606 OID 17929)
-- Name: tournament fk_tournament_winner_team; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tournament
    ADD CONSTRAINT fk_tournament_winner_team FOREIGN KEY (tournament_winner_team_id) REFERENCES public.team(team_id);


--
-- TOC entry 5020 (class 2606 OID 18014)
-- Name: team_squad fk_ts_player; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.team_squad
    ADD CONSTRAINT fk_ts_player FOREIGN KEY (player_id) REFERENCES public.player(player_id);


--
-- TOC entry 5021 (class 2606 OID 18009)
-- Name: team_squad fk_ts_team; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.team_squad
    ADD CONSTRAINT fk_ts_team FOREIGN KEY (team_id) REFERENCES public.team(team_id);


--
-- TOC entry 5022 (class 2606 OID 18019)
-- Name: team_squad fk_ts_tournament; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.team_squad
    ADD CONSTRAINT fk_ts_tournament FOREIGN KEY (tournament_id) REFERENCES public.tournament(tournament_id);


--
-- TOC entry 5014 (class 2606 OID 18165)
-- Name: player player_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.player
    ADD CONSTRAINT player_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(user_id) ON DELETE SET NULL;


--
-- TOC entry 5011 (class 2606 OID 18172)
-- Name: team team_manager_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.team
    ADD CONSTRAINT team_manager_user_id_fkey FOREIGN KEY (manager_user_id) REFERENCES public.users(user_id) ON DELETE SET NULL;


-- Completed on 2026-07-02 09:16:15

--
-- PostgreSQL database dump complete
--

\unrestrict TXpSCBheonCANJnObSjHzfZm6cfsCOyvvRxzKQ62YDofhc1uCH7Q26KkmVfJUAh

