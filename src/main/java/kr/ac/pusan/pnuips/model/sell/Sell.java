package kr.ac.pusan.pnuips.model.sell;

import kr.ac.pusan.pnuips.DatabaseManager;
import kr.ac.pusan.pnuips.model.Model;
import org.apache.commons.dbutils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Sell implements Model {

    private static final Logger logger = LoggerFactory.getLogger(Sell.class);

    private int itemcode;
    private int sellercode;
    private int price;
    private int numberOfStock;
    private int numberOfSales;

    public Sell() {

    }

    public Sell(int itemcode, int sellercode) {
        this.itemcode = itemcode;
        this.sellercode = sellercode;
    }

    public int getItemcode() {
        return itemcode;
    }

    public void setItemcode(int itemcode) {
        this.itemcode = itemcode;
    }

    public int getSellercode() {
        return sellercode;
    }

    public void setSellercode(int sellercode) {
        this.sellercode = sellercode;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getNumberOfStock() {
        return numberOfStock;
    }

    public void setNumberOfStock(int numberOfStock) {
        this.numberOfStock = numberOfStock;
    }

    public int getNumberOfSales() {
        return numberOfSales;
    }

    public void setNumberOfSales(int numberOfSales) {
        this.numberOfSales = numberOfSales;
    }

    @Override
    public void insert() throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DatabaseManager.getConnection();
            ps = con.prepareStatement("INSERT INTO pnuips.sell (itemcode, sellercode, price, numberOfStock, numberOfSales) VALUES (?, ?, ?, ?, ?)");
            ps.setInt(1, itemcode);
            ps.setInt(2, sellercode);
            ps.setInt(3, price);
            ps.setInt(4, numberOfStock);
            ps.setInt(5, numberOfSales);
            ps.executeUpdate();

            logger.debug("Insert sell. sell={}", this);
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(con);
        }
    }

    @Override
    public void load() throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DatabaseManager.getConnection();
            ps = con.prepareStatement("SELECT price, numberOfStock, numberOfSales FROM pnuips.sell WHERE itemcode=? AND sellercode=?");
            ps.setInt(1, itemcode);
            ps.setInt(2, sellercode);
            rs = ps.executeQuery();

            if (rs.next()) {
                price = rs.getInt("price");
                numberOfStock = rs.getInt("numberOfStock");
                numberOfSales = rs.getInt("numberOfSales");

                logger.debug("Load sell. sell={}", this);
            } else {
                throw new NullPointerException("Sell is not exist. itemcode=" + itemcode + ", sellercode=" + sellercode);
            }
        } finally {
            DbUtils.closeQuietly(con, ps, rs);
        }
    }

    @Override
    public void update() throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DatabaseManager.getConnection();
            ps = con.prepareStatement("UPDATE pnuips.sell SET price=?, numberOfStock=?, numberOfSales=? WHERE itemcode=? AND sellercode=?");
            ps.setInt(1, price);
            ps.setInt(2, numberOfStock);
            ps.setInt(3, numberOfSales);
            ps.setInt(4, itemcode);
            ps.setInt(5, sellercode);
            ps.executeUpdate();

            logger.debug("Update sell. sell={}", this);
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(con);
        }
    }

    @Override
    public void delete() throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DatabaseManager.getConnection();
            ps = con.prepareStatement("DELETE FROM pnuips.sell WHERE itemcode=? AND sellercode=?");
            ps.setInt(1, itemcode);
            ps.setInt(2, sellercode);
            ps.executeUpdate();

            logger.debug("Delete sell. sell={}", this);
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(con);
        }
    }

    @Override
    public boolean isExist() throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DatabaseManager.getConnection();
            ps = con.prepareStatement("SELECT itemcode, sellercode FROM pnuips.sell WHERE itemcode=? AND sellercode=?");
            ps.setInt(1, itemcode);
            ps.setInt(2, sellercode);
            rs = ps.executeQuery();

            return rs.next();
        } finally {
            DbUtils.closeQuietly(con, ps, rs);
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Sell{");
        sb.append("itemcode=").append(itemcode);
        sb.append(", sellercode=").append(sellercode);
        sb.append(", price=").append(price);
        sb.append(", numberOfStock=").append(numberOfStock);
        sb.append(", numberOfSales=").append(numberOfSales);
        sb.append('}');
        return sb.toString();
    }
}
