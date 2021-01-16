CREATE TABLE IF NOT EXISTS games (
    game_id VARCHAR(128) NOT NULL,
    game_key VARCHAR(128) NOT NULL,
    murker_addr VARCHAR(128) NOT NULL,
    winner INTEGER DEFAULT -1,
    plr_0 VARCHAR(64),
    plr_1 VARCHAR(64)
);
