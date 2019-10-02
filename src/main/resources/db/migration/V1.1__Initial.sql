CREATE TABLE Pairs
(
    id  SERIAL NOT NULL PRIMARY KEY,
    timestamp BIGINT,
    currency VARCHAR(255),
    prev_price NUMERIC,
    delta NUMERIC,
    last_price NUMERIC,
    increased BOOLEAN
);
it