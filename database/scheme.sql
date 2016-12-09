DROP TABLE IF EXISTS pnuips.account;
CREATE TABLE pnuips.account (
    id INTEGER NOT NULL,
    name VARCHAR(16) NOT NULL,
    password VARCHAR(64) NOT NULL,
    email VARCHAR (32) UNIQUE NOT NULL,
    lastname CHAR (4) NOT NULL,
    firstname CHAR (12) NOT NULL,
    birthday DATE NOT NULL,
    seller BOOLEAN NOT NULL DEFAULT false,
    grade INTEGER NOT NULL DEFAULT 0,
    PRIMARY KEY(id)
);

DROP TABLE IF EXISTS pnuips.item;
CREATE TABLE pnuips.item (
    id INTEGER NOT NULL,
    sellerId INTEGER NOT NULL,
    name VARCHAR (32) NOT NULL,
    price INTEGER NOT NULL CHECK(price > 0),
    brand VARCHAR (16) NOT NULL,
    stock INTEGER NOT NULL,
    purchased INTEGER NOT NULL DEFAULT 0,
    PRIMARY KEY(id),
    FOREIGN KEY(sellerId) REFERENCES pnuips.account(id) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS pnuips.cart;
CREATE TABLE pnuips.cart (
    accountId INTEGER NOT NULL,
    itemId INTEGER NOT NULL,
    PRIMARY KEY(accountId, itemId),
    FOREIGN KEY(accountId) REFERENCES pnuips.account(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY(itemId) REFERENCES pnuips.item(id) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS pnuips.order;
CREATE TABLE pnuips.order (
    sellerId INTEGER NOT NULL,
    itemId INTEGER NOT NULL,
    time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(sellerId, itemId),
    FOREIGN KEY(sellerId) REFERENCES pnuips.account(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY(itemId) REFERENCES pnuips.item(id) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS pnuips.couponType;
CREATE TABLE pnuips.couponType (
    type INTEGER NOT NULL,
    name VARCHAR (128) NOT NULL,
    discount INTEGER NOT NULL,
    PRIMARY KEY(type)
);

DROP TABLE IF EXISTS pnuips.coupon;
CREATE TABLE pnuips.coupon (
    type INTEGER NOT NULL,
    owener INTEGER NOT NULL,
    number VARCHAR (64) NOT NULL,
    due DATE NOT NULL,
    FOREIGN KEY(type) REFERENCES pnuips.couponType(type) ON DELETE CASCADE ON UPDATE CASCADE
);