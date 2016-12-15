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
}
