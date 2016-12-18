package kr.ac.pusan.pnuips.processor;

import kr.ac.pusan.pnuips.DatabaseManager;
import kr.ac.pusan.pnuips.model.sell.Seller;
import org.apache.commons.dbutils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SellerProcessor {

    private static final Logger logger = LoggerFactory.getLogger(SellerProcessor.class);

    public Seller searchSeller(int sellercode) {
        try {
            Seller seller = new Seller(sellercode);
            seller.load();

            return seller;
        } catch (SQLException e) {
            logger.error("Failed to search seller. sellercode=" + sellercode, e);
        }

        return null;
    }

    public Seller searchSeller(String sellername) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DatabaseManager.getConnection();
            ps = con.prepareStatement("SELECT sellercode, sellername FROM pnuips.seller WHERE sellername=?");
            ps.setString(1, sellername);
            rs = ps.executeQuery();

            if (rs.next()) {
                Seller seller = new Seller();
                seller.setSellercode(rs.getInt("sellercode"));
                seller.setSellername(rs.getString("sellername"));

                return seller;
            }
        } catch (SQLException e) {
            logger.error("Failed to search sell seller. sellername=" + sellername, e);
        } finally {
            DbUtils.closeQuietly(con, ps, rs);
        }

        return null;
    }

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
            logger.error("Failed to search sell count of seller. sellercode=" + sellercode, e);
        } finally {
            DbUtils.closeQuietly(con, ps, rs);
        }

        return count;
    }

    public int searchTotalPrice(int sellercode) {
        int price = 0;

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DatabaseManager.getConnection();
            ps = con.prepareStatement("SELECT SUM(price * count * (100 - discount) / 100) FROM pnuips.order NATURAL JOIN pnuips.sell WHERE sellercode=?");
            ps.setInt(1, sellercode);
            rs = ps.executeQuery();

            while (rs.next()) {
                price += rs.getInt(1);
            }
        } catch (SQLException e) {
            logger.error("Failed to search total price of seller. sellercode=" + sellercode, e);
        } finally {
            DbUtils.closeQuietly(con, ps, rs);
        }

        return price;
    }

    public int searchTotalPrice(int itemcode, int sellercode) {
        int price = 0;

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DatabaseManager.getConnection();
            ps = con.prepareStatement("SELECT SUM(price * count * (100 - discount) / 100) FROM pnuips.order NATURAL JOIN pnuips.sell WHERE itemcode=? AND sellercode=?");
            ps.setInt(1, itemcode);
            ps.setInt(2, sellercode);
            rs = ps.executeQuery();

            while (rs.next()) {
                price += rs.getInt(1);
            }
        } catch (SQLException e) {
            logger.error("Failed to search total price of seller. sellercode=" + sellercode, e);
        } finally {
            DbUtils.closeQuietly(con, ps, rs);
        }

        return price;
    }
}
