package kr.ac.pusan.pnuips.model.order;

import kr.ac.pusan.pnuips.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Order {

    private int itemcode;
    private int count;
    private int discount;
    private String purchaser;
    private Timestamp time;

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

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getPurchaser() {
        return purchaser;
    }

    public void setPurchaser(String purchaser) {
        this.purchaser = purchaser;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public void insert() throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DatabaseManager.getConnection();
            ps = con.prepareStatement("INSERT INTO pnuips.order (itemcode, purchaser, ordercount, discount, ordertime) VALUES (?, ?, ?, ?, ?)");
            ps.setInt(1, itemcode);
            ps.setString(2, purchaser);
            ps.setInt(3, count);
            ps.setInt(4, discount);
            ps.setTimestamp(5, time);
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
        final StringBuilder sb = new StringBuilder("Order{");
        sb.append("itemcode=").append(itemcode);
        sb.append(", count=").append(count);
        sb.append(", discount=").append(discount);
        sb.append(", purchaser='").append(purchaser).append('\'');
        sb.append(", time=").append(time);
        sb.append('}');
        return sb.toString();
    }
}
