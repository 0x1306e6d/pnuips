package kr.ac.pusan.pnuips.model.coupon;

import kr.ac.pusan.pnuips.DatabaseManager;
import kr.ac.pusan.pnuips.model.Model;
import org.apache.commons.dbutils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Coupon implements Model {

    private static final Logger logger = LoggerFactory.getLogger(Coupon.class);

    private int type;
    private String owener;

    public Coupon() {
    }

    public Coupon(int type, String owener) {
        this.type = type;
        this.owener = owener;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getOwener() {
        return owener;
    }

    public void setOwener(String owener) {
        this.owener = owener;
    }

    @Override
    public void insert() throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DatabaseManager.getConnection();
            ps = con.prepareStatement("INSERT INTO pnuips.coupon (type, owener) VALUES (?, ?)");
            ps.setInt(1, type);
            ps.setString(2, owener);
            ps.executeUpdate();

            logger.trace("Insert coupon. {}", this);
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(con);
        }
    }

    @Override
    public void load() throws SQLException {
        // Do nothing because all attributes are primary key
    }

    @Override
    public void update() throws SQLException {
        // Do nothing because all attributes are primary key
    }

    @Override
    public void delete() throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DatabaseManager.getConnection();
            ps = con.prepareStatement("DELETE FROM pnuips.coupon WHERE type=? AND owener=?");
            ps.setInt(1, type);
            ps.setString(2, owener);
            ps.executeUpdate();

            logger.debug("Delete coupon. {}", this);
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
            ps = con.prepareStatement("SELECT type, owener FROM pnuips.coupon WHERE type=? AND owener=?");
            ps.setInt(1, type);
            ps.setString(2, owener);
            rs = ps.executeQuery();

            return rs.next();
        } finally {
            DbUtils.closeQuietly(con, ps, rs);
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Coupon{");
        sb.append("type=").append(type);
        sb.append(", owener='").append(owener).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
