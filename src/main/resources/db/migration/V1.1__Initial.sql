CREATE TABLE Pairs
(
    id  SERIAL NOT NULL PRIMARY KEY,
    timestamp BIGINT,
    currency VARCHAR(255),
    prev_price FLOAT,
    delta FLOAT,
    last_price FLOAT,
    increased INTEGER
);
