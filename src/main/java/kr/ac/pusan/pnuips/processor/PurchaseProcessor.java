package kr.ac.pusan.pnuips.processor;

import kr.ac.pusan.pnuips.DatabaseManager;
import kr.ac.pusan.pnuips.model.coupon.Coupon;
import org.apache.commons.dbutils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class PurchaseProcessor {

    private static final Logger logger = LoggerFactory.getLogger(PurchaseProcessor.class);

    /**
     * 상품을 구매한다
     *
     * @param itemcode   상품 itemcode
     * @param sellercode 판매자 sellercode
     * @param purchaser  구매자 email
     * @param count      구매 갯수
     * @param discount   총 할인율
     * @throws SQLException 구매를 실패할 경우
     */
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
                ps = con.prepareStatement("UPDATE pnuips.account SET totalPrice=(totalPrice + (SELECT (price * ? * (100 - ?) / 100) FROM pnuips.sell WHERE itemcode=? AND sellercode=?)) WHERE email=?");
                ps.setInt(1, count);
                ps.setInt(2, discount);
                ps.setInt(3, itemcode);
                ps.setInt(4, sellercode);
                ps.setString(5, purchaser);
                ps.executeUpdate();

                logger.debug("Update total price.");
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

            try {
                ps = con.prepareStatement("UPDATE pnuips.account SET grade=1 WHERE email=? AND grade=0 AND totalPrice > 200000");
                ps.setString(1, purchaser);
                if (ps.executeUpdate() > 0) {
                    Coupon coupon = new Coupon(5, purchaser);
                    coupon.insert();
                }
            } finally {
                DbUtils.closeQuietly(ps);
            }

            try {
                ps = con.prepareStatement("UPDATE pnuips.account SET grade=2 WHERE email=? AND grade=1 AND totalPrice > 500000");
                ps.setString(1, purchaser);
                if (ps.executeUpdate() > 0) {
                    Coupon coupon = new Coupon(0, purchaser);
                    coupon.insert();
                }
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
