package kr.ac.pusan.pnuips.model.coupon;

import kr.ac.pusan.pnuips.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CouponType {

    private int type;
    private String name;
    private int discount;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public void insert() throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DatabaseManager.getConnection();
            ps = con.prepareStatement("INSERT INTO pnuips.couponType (type, name, discount) VALUES (?, ?, ?)");
            ps.setInt(1, type);
            ps.setString(2, name);
            ps.setInt(3, discount);
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
        final StringBuilder sb = new StringBuilder("CouponType{");
        sb.append("type=").append(type);
        sb.append(", name='").append(name).append('\'');
        sb.append(", discount=").append(discount);
        sb.append('}');
        return sb.toString();
    }
}
