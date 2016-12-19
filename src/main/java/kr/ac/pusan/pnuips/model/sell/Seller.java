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

public class Seller implements Model {

    private static final Logger logger = LoggerFactory.getLogger(Seller.class);

    private int sellercode;
    private String sellername;

    public Seller() {

    }

    public Seller(int sellercode) {
        this.sellercode = sellercode;
    }

    public int getSellercode() {
        return sellercode;
    }

    public void setSellercode(int sellercode) {
        this.sellercode = sellercode;
    }

    public String getSellername() {
        return sellername;
    }

    public void setSellername(String sellername) {
        this.sellername = sellername;
    }

    @Override
    public void insert() throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DatabaseManager.getConnection();
            ps = con.prepareStatement("INSERT INTO pnuips.seller (sellercode, sellername) VALUES (?, ?)");
            ps.setInt(1, sellercode);
            ps.setString(2, sellername);
            ps.executeUpdate();

            logger.trace("Insert seller. {}", this);
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
            ps = con.prepareStatement("SELECT sellername FROM pnuips.seller WHERE sellercode=?");
            ps.setInt(1, sellercode);
            rs = ps.executeQuery();

            if (rs.next()) {
                sellername = rs.getString("sellername");

                logger.trace("Load seller. {}", this);
            } else {
                throw new NullPointerException("Seller is not exist. sellercode=" + sellercode);
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
            ps = con.prepareStatement("UPDATE pnuips.seller SET sellername=? WHERE sellercode=?");
            ps.setString(1, sellername);
            ps.setInt(2, sellercode);
            ps.executeUpdate();

            logger.trace("Update seller. {}", this);
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
            ps = con.prepareStatement("DELETE FROM pnuips.seller WHERE sellercode=?");
            ps.setInt(1, sellercode);
            ps.executeUpdate();

            logger.trace("Delete seller. {}", this);
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
            ps = con.prepareStatement("SELECT sellercode FROM pnuips.seller WHERE sellercode=?");
            ps.setInt(1, sellercode);
            rs = ps.executeQuery();

            return rs.next();
        } finally {
            DbUtils.closeQuietly(con, ps, rs);
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Seller{");
        sb.append("sellercode=").append(sellercode);
        sb.append(", sellername='").append(sellername).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
