package kr.ac.pusan.pnuips.processor;

import kr.ac.pusan.pnuips.DatabaseManager;
import org.apache.commons.dbutils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class PurchaseProcessor {

    private static final Logger logger = LoggerFactory.getLogger(PurchaseProcessor.class);

    public void purchase(int itemcode, int sellercode, String purchaser, int count, int discount) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DatabaseManager.getConnection();
            con.setAutoCommit(false); // Transaction

            try {
                ps = con.prepareStatement("INSERT INTO pnuips.order (itemcode, sellercode, purchaser, count, discount, time) VALUES (?, ?, ?, ?, ?, ?)");
                ps.setInt(1, itemcode);
                ps.setInt(2, sellercode);
                ps.setString(3, purchaser);
                ps.setInt(4, count);
                ps.setInt(5, discount);
                ps.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
                ps.executeUpdate();

                logger.debug("Insert order.");
            } finally {
                DbUtils.closeQuietly(ps);
            }

            try {
                ps = con.prepareStatement("UPDATE pnuips.sell SET numberOfSales=(numberOfSales + ?), numberOfStock=(numberOfStock - ?) WHERE itemcode=? AND sellercode=?");
                ps.setInt(1, count);
                ps.setInt(2, count);
                ps.setInt(3, itemcode);
                ps.setInt(4, sellercode);
                ps.executeUpdate();

                logger.debug("Update sell.");
            } finally {
                DbUtils.closeQuietly(ps);
            }

            try {
                ps = con.prepareStatement("DELETE FROM pnuips.cart WHERE itemcode=? AND sellercode=? AND owener=?");
                ps.setInt(1, itemcode);
                ps.setInt(2, sellercode);
                ps.setString(3, purchaser);
                ps.executeUpdate();

                logger.debug("Delete cart.");
            } finally {
                DbUtils.closeQuietly(ps);
            }

            con.commit();
            logger.debug("Purchase item.");
        } finally {
            if (con != null) {
                con.setAutoCommit(true);
            }
            DbUtils.closeQuietly(con, ps, rs);
        }
    }
}
