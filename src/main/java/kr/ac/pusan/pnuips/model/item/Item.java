package kr.ac.pusan.pnuips.model.item;

import kr.ac.pusan.pnuips.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Item {

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
        final StringBuilder sb = new StringBuilder("Item{");
        sb.append("itemcode=").append(itemcode);
        sb.append(", itemname='").append(itemname).append('\'');
        sb.append(", brand='").append(brand).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
