package kr.ac.pusan.pnuips.model.coupon;

import kr.ac.pusan.pnuips.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Coupon {

    private int type;
    private String owener;

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

    public void insert() throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DatabaseManager.getConnection();
            ps = con.prepareStatement("INSERT INTO pnuips.coupon (type, owener) VALUES (?, ?)");
            ps.setInt(1, type);
            ps.setString(2, owener);
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
        final StringBuilder sb = new StringBuilder("Coupon{");
        sb.append("type=").append(type);
        sb.append(", owener='").append(owener).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
