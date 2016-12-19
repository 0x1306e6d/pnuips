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

    /**
     * 판매자를 검색한다
     *
     * @param sellercode 판매자 sellercode
     * @return 판매자 객체
     */
    public Seller searchSellerBySellercode(int sellercode) {
        logger.debug("Search seller by sellercode request. sellercode={}", sellercode);
        try {
            Seller seller = new Seller(sellercode);
            if (seller.isExist()) {
                seller.load();

                return seller;
            }
        } catch (SQLException e) {
            logger.error("Failed to search seller by sellercode. sellercode=" + sellercode, e);
        }
        return null;
    }

    /**
     * 판매자를 검색한다
     *
     * @param sellername 판매자 sellername
     * @return 판매자 객체
     */
    public Seller searchSeller(String sellername) {
        logger.debug("Search seller by sellername request. sellername={}", sellername);

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DatabaseManager.getConnection();
            ps = con.prepareStatement("SELECT * FROM pnuips.seller WHERE sellername=?");
            ps.setString(1, sellername);
            rs = ps.executeQuery();

            if (rs.next()) {
                return Seller.fromResultSet(rs);
            }
        } catch (SQLException e) {
            logger.error("Failed to search sell seller by sellername. sellername=" + sellername, e);
        } finally {
            DbUtils.closeQuietly(con, ps, rs);
        }

        return null;
    }

    /**
     * 판매자가 판매한 상품의 총 개수를 구한다
     *
     * @param sellercode 판매자 sellercode
     * @return 판매한 상품의  총 개수
     */
    public int getSalesCount(int sellercode) {
        logger.debug("Get sales count request. sellercode={}", sellercode);
        int count = 0;

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DatabaseManager.getConnection();
            ps = con.prepareStatement("SELECT SUM(numberOfSales) FROM pnuips.sell WHERE sellercode=?");
            ps.setInt(1, sellercode);
            rs = ps.executeQuery();

            while (rs.next()) {
                count += rs.getInt(1);
            }
        } catch (SQLException e) {
            logger.error("Failed to get sales count. sellercode=" + sellercode, e);
        } finally {
            DbUtils.closeQuietly(con, ps, rs);
        }

        return count;
    }

    /**
     * 판매자가 판매한 상품들의 총 금액을 구한다
     *
     * @param sellercode 판매자 sellercode
     * @return 판매한 상품들의 총 금액
     */
    public int getTotalSalesPrice(int sellercode) {
        logger.debug("Get total sales price request. sellercode={}", sellercode);
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
            logger.error("Failed to get total sales price. sellercode=" + sellercode, e);
        } finally {
            DbUtils.closeQuietly(con, ps, rs);
        }

        return price;
    }

    /**
     * 판매자가 판매한 상품의 총 금액을 구한다
     *
     * @param itemcode   상품 itemcode
     * @param sellercode 판매자 sellercode
     * @return 판매한 상품의 총 금액
     */
    public int getTotalSalesPrice(int itemcode, int sellercode) {
        logger.debug("Get total sales price request. itemcode={}, sellercode={}", itemcode, sellercode);
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
            logger.error("Failed to get total sales price. itemcode=" + itemcode + ", sellercode=" + sellercode, e);
        } finally {
            DbUtils.closeQuietly(con, ps, rs);
        }

        return price;
    }
}
