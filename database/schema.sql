ALTER TABLE pnuips.sell DROP CONSTRAINT sell_itemcode_fkey;
ALTER TABLE pnuips.sell DROP CONSTRAINT sell_sellercode_fkey;
ALTER TABLE pnuips.cart DROP CONSTRAINT cart_owener_fkey;
ALTER TABLE pnuips.cart DROP CONSTRAINT cart_itemcode_fkey;
ALTER TABLE pnuips.cart DROP CONSTRAINT cart_sellercode_fkey;
ALTER TABLE pnuips.order DROP CONSTRAINT order_itemcode_fkey;
ALTER TABLE pnuips.order DROP CONSTRAINT order_sellercode_fkey;
ALTER TABLE pnuips.order DROP CONSTRAINT order_purchaser_fkey;
ALTER TABLE pnuips.coupon DROP CONSTRAINT coupon_type_fkey;
ALTER TABLE pnuips.coupon DROP CONSTRAINT coupon_owener_fkey;

DROP TABLE IF EXISTS pnuips.account;
CREATE TABLE pnuips.account (
    email VARCHAR(32) NOT NULL,
    password VARCHAR(64) NOT NULL,
    firstname VARCHAR(32) NOT NULL,
    lastname VARCHAR(16) NOT NULL,
    birthday DATE NOT NULL,
    grade INTEGER NOT NULL DEFAULT 0,
    totalPrice BIGINT NOT NULL DEFAULT 0,
    PRIMARY KEY(email)
);

DROP TABLE IF EXISTS pnuips.item;
CREATE TABLE pnuips.item (
    itemcode INTEGER NOT NULL,
    itemname VARCHAR(128) NOT NULL,
    brand VARCHAR(64) NOT NULL,
    PRIMARY KEY (itemcode)
);

DROP TABLE IF EXISTS pnuips.seller;
CREATE TABLE pnuips.seller (
    sellercode INTEGER NOT NULL,
    sellername VARCHAR(32) NOT NULL,
    PRIMARY KEY(sellercode)
);

DROP TABLE IF EXISTS pnuips.sell;
CREATE TABLE pnuips.sell (
    itemcode INTEGER NOT NULL,
    sellercode INTEGER NOT NULL,
    price INTEGER NOT NULL CHECK (price > 0),
    numberOfStock INTEGER NOT NULL,
    numberOfSales INTEGER NOT NULL DEFAULT 0,
    PRIMARY KEY (itemcode, sellercode),
    FOREIGN KEY (itemcode) REFERENCES pnuips.item(itemcode) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (sellercode) REFERENCES pnuips.seller(sellercode) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS pnuips.cart;
CREATE TABLE pnuips.cart (
    itemcode INTEGER NOT NULL,
    sellercode INTEGER NOT NULL,
    owener VARCHAR(32) NOT NULL,
    count INTEGER NOT NULL DEFAULT 0,
    PRIMARY KEY(itemcode, sellercode, owener),
    FOREIGN KEY(itemcode) REFERENCES pnuips.item(itemcode) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY(sellercode) REFERENCES pnuips.seller(sellercode) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY(owener) REFERENCES pnuips.account(email) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS pnuips.order;
CREATE TABLE pnuips.order (
    itemcode INTEGER NOT NULL,
    sellercode INTEGER NOT NULL,
    purchaser VARCHAR(32) NOT NULL,
    count INTEGER NOT NULL DEFAULT 0,
    discount INTEGER NOT NULL DEFAULT 0,
    time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(itemcode, sellercode, purchaser, time),
    FOREIGN KEY(itemcode) REFERENCES pnuips.item(itemcode) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY(sellercode) REFERENCES pnuips.seller(sellercode) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY(purchaser) REFERENCES pnuips.account(email) ON DELETE CASCADE ON UPDATE CASCADE
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
    owener VARCHAR(32) NOT NULL,
    PRIMARY KEY(type, owener),
    FOREIGN KEY(type) REFERENCES pnuips.couponType(type) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY(owener) REFERENCES pnuips.account(email) ON DELETE CASCADE ON UPDATE CASCADE
);