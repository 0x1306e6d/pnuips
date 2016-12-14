package kr.ac.pusan.pnuips.model.sell;

import kr.ac.pusan.pnuips.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Seller {

    private int sellercode;
    private String sellername;

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

    public void insert() throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DatabaseManager.getConnection();
            ps = con.prepareStatement("INSERT INTO pnuips.seller (sellercode, sellername) VALUES (?, ?)");
            ps.setInt(1, sellercode);
            ps.setString(2, sellername);
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
        final StringBuilder sb = new StringBuilder("Seller{");
        sb.append("sellercode=").append(sellercode);
        sb.append(", sellername='").append(sellername).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
