package kr.ac.pusan.pnuips.model.sell;

import kr.ac.pusan.pnuips.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Sell {

    private int itemcode;
    private int sellercode;
    private int price;
    private int numberOfStock;
    private int numberOfSales;

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getNumberOfStock() {
        return numberOfStock;
    }

    public void setNumberOfStock(int numberOfStock) {
        this.numberOfStock = numberOfStock;
    }

    public int getNumberOfSales() {
        return numberOfSales;
    }

    public void setNumberOfSales(int numberOfSales) {
        this.numberOfSales = numberOfSales;
    }

    public void insert() throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DatabaseManager.getConnection();
            ps = con.prepareStatement("INSERT INTO pnuips.sell (itemcode, sellercode, price, numberOfStock, numberOfSales) VALUES (?, ?, ?, ?, ?)");
            ps.setInt(1, itemcode);
            ps.setInt(2, sellercode);
            ps.setInt(3, price);
            ps.setInt(4, numberOfStock);
            ps.setInt(5, numberOfSales);
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
        final StringBuilder sb = new StringBuilder("Sell{");
        sb.append("itemcode=").append(itemcode);
        sb.append(", sellercode=").append(sellercode);
        sb.append(", price=").append(price);
        sb.append(", numberOfStock=").append(numberOfStock);
        sb.append(", numberOfSales=").append(numberOfSales);
        sb.append('}');
        return sb.toString();
    }
}
