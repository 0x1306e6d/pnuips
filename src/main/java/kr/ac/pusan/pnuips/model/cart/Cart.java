package kr.ac.pusan.pnuips.model.cart;

import kr.ac.pusan.pnuips.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Cart {

    private String owener;
    private int itemcode;
    private int count;

    public String getOwener() {
        return owener;
    }

    public void setOwener(String owener) {
        this.owener = owener;
    }

    public int getItemcode() {
        return itemcode;
    }

    public void setItemcode(int itemcode) {
        this.itemcode = itemcode;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void insert() throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DatabaseManager.getConnection();
            ps = con.prepareStatement("INSERT INTO pnuips.cart (email, itemcode, count) VALUES (?, ?, ?)");
            ps.setString(1, owener);
            ps.setInt(2, itemcode);
            ps.setInt(3, count);
            ps.executeUpdate();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Cart{");
        sb.append("owener='").append(owener).append('\'');
        sb.append(", itemcode=").append(itemcode);
        sb.append(", count=").append(count);
        sb.append('}');
        return sb.toString();
    }
}
