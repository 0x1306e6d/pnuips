package kr.ac.pusan.pnuips.processor;

import kr.ac.pusan.pnuips.DatabaseConstants;
import kr.ac.pusan.pnuips.item.Item;

import java.sql.*;

public class ItemProcessor {

    public Item searchItem(int itemcode) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            Class.forName(DatabaseConstants.DRIVER);
            con = DriverManager.getConnection(
                    DatabaseConstants.URL,
                    DatabaseConstants.USER,
                    DatabaseConstants.PASSWORD
            );
            ps = con.prepareStatement("SELECT itemcode, itemname, brand FROM pnuips.item WHERE itemcode=?");
            ps.setInt(1, itemcode);
            rs = ps.executeQuery();

            if (rs.next()) {
                Item item = new Item();
                item.setItemcode(rs.getInt("itemcode"));
                item.setItemname(rs.getString("itemname"));
                item.setBrand(rs.getString("brand"));

                return item;
            }
        } catch (SQLException | ClassNotFoundException e) {
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
