package kr.ac.pusan.pnuips.model.item;

import kr.ac.pusan.pnuips.DatabaseManager;
import kr.ac.pusan.pnuips.model.Model;
import org.apache.commons.dbutils.DbUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Item implements Model {

    private int itemcode;
    private String itemname;
    private String brand;

    public int getItemcode() {
        return itemcode;
    }

    public void setItemcode(int itemcode) {
        this.itemcode = itemcode;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public void insert() throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DatabaseManager.getConnection();
            ps = con.prepareStatement("INSERT INTO pnuips.item (itemcode, itemname, brand) VALUES (?, ?, ?)");
            ps.setInt(1, itemcode);
            ps.setString(2, itemname);
            ps.setString(3, brand);
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
            ps = con.prepareStatement("SELECT itemname, brand FROM pnuips.item WHERE itemcode=?");
            ps.setInt(1, itemcode);
            rs = ps.executeQuery();

            if (rs.next()) {
                itemname = rs.getString("itemname");
                brand = rs.getString("brand");
            } else {
                throw new NullPointerException("Item is not exist. itemcode=" + itemcode);
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
            ps = con.prepareStatement("UPDATE pnuips.item SET itemname=?, brand=? WHERE itemcode=?");
            ps.setString(1, itemname);
            ps.setString(2, brand);
            ps.setInt(3, itemcode);
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
            ps = con.prepareStatement("DELETE FROM pnuips.item WHERE itemcode=?");
            ps.setInt(1, itemcode);
            ps.executeUpdate();
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(con);
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Item{");
        sb.append("itemcode=").append(itemcode);
        sb.append(", itemname='").append(itemname).append('\'');
        sb.append(", brand='").append(brand).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
