CREATE TABLE BTCUSD
(
    id  SERIAL NOT NULL PRIMARY KEY,
    timestamp VARCHAR(255),
    currency VARCHAR(255),
    prev_price INTEGER,
    delta INTEGER,
    last_price INTEGER,
    increased INTEGER
);
