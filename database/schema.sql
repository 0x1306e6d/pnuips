DROP TABLE IF EXISTS pnuips.account;
CREATE TABLE pnuips.account (
    email VARCHAR(32) NOT NULL,
    password VARCHAR(64) NOT NULL,
    firstname VARCHAR(16) NOT NULL,
    lastname VARCHAR(8) NOT NULL,
    birthday DATE NOT NULL,
    grade INTEGER NOT NULL DEFAULT 0,
    PRIMARY KEY (email)
);

DROP TABLE IF EXISTS pnuips.seller;
CREATE TABLE pnuips.seller (
    sellercode INTEGER NOT NULL,
    email VARCHAR(32) NOT NULL,
    PRIMARY KEY(sellercode),
    FOREIGN KEY(email) REFERENCES pnuips.account(email) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS pnuips.item;
CREATE TABLE pnuips.item (
    itemcode INTEGER NOT NULL,
    itemname VARCHAR(128) NOT NULL,
    brand VARCHAR(64) NOT NULL,
    price INTEGER NOT NULL CHECK (price > 0),
    sellercode INTEGER NOT NULL,
    sellername VARCHAR(16) NOT NULL,
    numberOfStock INTEGER NOT NULL,
    numberOfSales INTEGER NOT NULL DEFAULT 0,
    PRIMARY KEY (itemcode),
    FOREIGN KEY (sellercode) REFERENCES pnuips.seller(sellercode) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS pnuips.cart;
CREATE TABLE pnuips.cart (
    email VARCHAR(32) NOT NULL,
    itemcode INTEGER NOT NULL,
    count INTEGER NOT NULL DEFAULT 0
    PRIMARY KEY(email, itemcode),
    FOREIGN KEY(email) REFERENCES pnuips.account(email) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY(itemcode) REFERENCES pnuips.item(itemcode) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS pnuips.order;
CREATE TABLE pnuips.order (
    itemcode INTEGER NOT NULL,
    purchaser VARCHAR(32) NOT NULL,
    count INTEGER NOT NULL DEFAULT 0
    discount INTEGER NOT NULL DEFAULT 0,
    time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(itemcode, purchaser),
    FOREIGN KEY(itemcode) REFERENCES pnuips.item(itemcode) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY(purchaser) REFERENCES pnuips.account(email) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS pnuips.FcouponType;
CREATE TABLE pnuips.couponType (
    type INTEGER NOT NULL,
    name VARCHAR (128) NOT NULL,
    discount INTEGER NOT NULL,
    PRIMARY KEY(type)
);

DROP TABLE IF EXISTS pnuips.coupon;
CREATE TABLE pnuips.coupon (
    type INTEGER NOT NULL,
    owener VARCHAR(32) NOT NULL,
    due DATE NOT NULL,
    PRIMARY KEY(type, owener),
    FOREIGN KEY(type) REFERENCES pnuips.couponType(type) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY(owener) REFERENCES pnuips.account(email) ON DELETE CASCADE ON UPDATE CASCASE
);