CREATE TABLE Pairs
(
    id  SERIAL NOT NULL PRIMARY KEY,
    timestamp VARCHAR(255),
    currency VARCHAR(255),
    prev_price FLOAT,
    delta FLOAT,
    last_price FLOAT,
    increased INTEGER
);
