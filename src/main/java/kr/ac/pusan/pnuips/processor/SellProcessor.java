package kr.ac.pusan.pnuips.processor;

import com.google.common.collect.Lists;
import kr.ac.pusan.pnuips.DatabaseManager;
import kr.ac.pusan.pnuips.model.item.Item;
import kr.ac.pusan.pnuips.model.sell.Sell;
import kr.ac.pusan.pnuips.model.sell.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SellProcessor {

    public Sell searchSell(int sellercode, int itemcode) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DatabaseManager.getConnection();
            ps = con.prepareStatement("SELECT * FROM (pnuips.sell NATURAL JOIN pnuips.item) NATURAL JOIN pnuips.seller WHERE sellercode=? AND itemcode=?");
            ps.setInt(1, sellercode);
            ps.setInt(2, itemcode);
            rs = ps.executeQuery();

            if (rs.next()) {
                Item item = new Item();
                item.setItemcode(rs.getInt("itemcode"));
                item.setItemname(rs.getString("itemname"));
                item.setBrand(rs.getString("brand"));

                Seller seller = new Seller();
                seller.setSellercode(rs.getInt("sellercode"));
                seller.setSellername(rs.getString("sellername"));

                Sell sell = new Sell();
                sell.setItem(item);
                sell.setSeller(seller);
                sell.setPrice(rs.getInt("price"));
                sell.setNumberOfStock(rs.getInt("numberOfStock"));
                sell.setNumberOfSales(rs.getInt("numberOfSales"));

                return sell;
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

    public List<Sell> searchSellList(int start) {
        List<Sell> sellList = Lists.newArrayList();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DatabaseManager.getConnection();
            ps = con.prepareStatement("SELECT * FROM (pnuips.sell NATURAL JOIN pnuips.item) NATURAL JOIN pnuips.seller LIMIT 10  OFFSET " + start);
            rs = ps.executeQuery();

            while (rs.next()) {
                Item item = new Item();
                item.setItemcode(rs.getInt("itemcode"));
                item.setItemname(rs.getString("itemname"));
                item.setBrand(rs.getString("brand"));

                Seller seller = new Seller();
                seller.setSellercode(rs.getInt("sellercode"));
                seller.setSellername(rs.getString("sellername"));

                Sell sell = new Sell();
                sell.setItem(item);
                sell.setSeller(seller);
                sell.setPrice(rs.getInt("price"));
                sell.setNumberOfStock(rs.getInt("numberOfStock"));
                sell.setNumberOfSales(rs.getInt("numberOfSales"));

                sellList.add(sell);
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

        return sellList;
    }

    public List<Sell> searchBestseller() {
        List<Sell> sellList = Lists.newArrayList();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DatabaseManager.getConnection();
            ps = con.prepareStatement("SELECT * FROM (pnuips.sell NATURAL JOIN pnuips.item) NATURAL JOIN pnuips.seller ORDER BY numberOfSales DESC LIMIT 10");
            rs = ps.executeQuery();

            while (rs.next()) {
                Item item = new Item();
                item.setItemcode(rs.getInt("itemcode"));
                item.setItemname(rs.getString("itemname"));
                item.setBrand(rs.getString("brand"));

                Seller seller = new Seller();
                seller.setSellercode(rs.getInt("sellercode"));
                seller.setSellername(rs.getString("sellername"));

                Sell sell = new Sell();
                sell.setItem(item);
                sell.setSeller(seller);
                sell.setPrice(rs.getInt("price"));
                sell.setNumberOfStock(rs.getInt("numberOfStock"));
                sell.setNumberOfSales(rs.getInt("numberOfSales"));

                sellList.add(sell);
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

        return sellList;
    }
}
