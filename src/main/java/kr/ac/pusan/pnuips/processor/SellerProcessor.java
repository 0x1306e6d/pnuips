package kr.ac.pusan.pnuips.processor;

import kr.ac.pusan.pnuips.DatabaseManager;
import kr.ac.pusan.pnuips.model.sell.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SellerProcessor {

    public int searchSellCount(int sellercode) {
        int count = 0;

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DatabaseManager.getConnection();
            ps = con.prepareStatement("SELECT SUM(numberOfSales) FROM pnuips.sell WHERE sellercode=?");
            ps.setInt(1, sellercode);
            rs = ps.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
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
        return count;
    }

    public Seller searchSeller(int sellercode) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DatabaseManager.getConnection();
            ps = con.prepareStatement("SELECT * FROM pnuips.seller WHERE sellercode=?");
            ps.setInt(1, sellercode);
            rs = ps.executeQuery();

            if (rs.next()) {
                Seller seller = new Seller();
                seller.setSellercode(rs.getInt("sellercode"));
                seller.setSellername(rs.getString("sellername"));

                return seller;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
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

        return null;
    }
}
