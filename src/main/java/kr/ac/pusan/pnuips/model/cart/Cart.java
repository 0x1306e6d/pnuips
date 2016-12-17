package kr.ac.pusan.pnuips.model.cart;

import kr.ac.pusan.pnuips.DatabaseManager;
import kr.ac.pusan.pnuips.model.Model;
import org.apache.commons.dbutils.DbUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Cart implements Model {

    private int itemcode;
    private int sellercode;
    private String owener;
    private int count;

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

    public String getOwener() {
        return owener;
    }

    public void setOwener(String owener) {
        this.owener = owener;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public void insert() throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DatabaseManager.getConnection();
            ps = con.prepareStatement("INSERT INTO pnuips.cart (itemcode, sellercode, owener, count) VALUES (?, ?, ?, ?)");
            ps.setInt(1, itemcode);
            ps.setInt(2, sellercode);
            ps.setString(3, owener);
            ps.setInt(4, count);
            ps.executeUpdate();
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
            ps = con.prepareStatement("SELECT count FROM pnuips.cart WHERE itemcode=? AND sellercode=? AND owener=?");
            ps.setInt(1, itemcode);
            ps.setInt(2, sellercode);
            ps.setString(3, owener);
            rs = ps.executeQuery();

            if (rs.next()) {
                count = rs.getInt("count");
            } else {
                throw new NullPointerException("Cart is not exist. owener=" + owener + ", itemcode=" + itemcode);
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
            ps = con.prepareStatement("UPDATE pnuips.cart SET count=? WHERE itemcode=? AND sellercode=? AND owener=?");
            ps.setInt(1, count);
            ps.setInt(2, itemcode);
            ps.setInt(3, sellercode);
            ps.setString(4, owener);
            ps.executeUpdate();
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
            ps = con.prepareStatement("DELETE FROM pnuips.cart WHERE itemcode=? AND sellercode=? AND owener=?");
            ps.setInt(1, itemcode);
            ps.setInt(2, sellercode);
            ps.setString(3, owener);
            ps.executeUpdate();
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
            ps = con.prepareStatement("SELECT owener, itemcode FROM pnuips.cart WHERE itemcode=? AND sellercode=? AND owener=?");
            ps.setInt(1, itemcode);
            ps.setInt(2, sellercode);
            ps.setString(3, owener);
            rs = ps.executeQuery();

            return rs.next();
        } finally {
            DbUtils.closeQuietly(con, ps, rs);
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Cart{");
        sb.append("itemcode=").append(itemcode);
        sb.append(", sellercode=").append(sellercode);
        sb.append(", owener='").append(owener).append('\'');
        sb.append(", count=").append(count);
        sb.append('}');
        return sb.toString();
    }
}
