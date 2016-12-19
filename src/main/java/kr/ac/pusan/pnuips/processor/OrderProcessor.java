package kr.ac.pusan.pnuips.processor;

import com.google.common.collect.Lists;
import kr.ac.pusan.pnuips.DatabaseManager;
import kr.ac.pusan.pnuips.model.order.Order;
import org.apache.commons.dbutils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderProcessor {

    private static final Logger logger = LoggerFactory.getLogger(OrderProcessor.class);

    /**
     * 사용자의 구매 내역을 검색한다
     *
     * @param purchaser 사용자 email
     * @return 구매 내역
     */
    public List<Order> searchOrderListByPurchaser(String purchaser) {
        logger.debug("Search order list by purchaser request. purchaser={}", purchaser);
        List<Order> orderList = Lists.newArrayList();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DatabaseManager.getConnection();
            ps = con.prepareStatement("SELECT * FROM pnuips.order WHERE purchaser=?");
            ps.setString(1, purchaser);
            rs = ps.executeQuery();

            while (rs.next()) {
                orderList.add(Order.fromResultSet(rs));
            }
        } catch (SQLException e) {
            logger.error("Failed to search order list by purchaser. purchaser=" + purchaser, e);
        } finally {
            DbUtils.closeQuietly(con, ps, rs);
        }

        return orderList;
    }

    /**
     * 판매자의 판매 내역을 검색한다
     *
     * @param selloercode 판매자 sellercode
     * @return 판매 내역
     */
    public List<Order> searchOrderListBySellercode(int selloercode) {
        logger.debug("Search order list by sellercode request. sellercode={}", selloercode);
        List<Order> orderList = Lists.newArrayList();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DatabaseManager.getConnection();
            ps = con.prepareStatement("SELECT * FROM pnuips.order WHERE sellercode=?");
            ps.setInt(1, selloercode);
            rs = ps.executeQuery();

            while (rs.next()) {
                orderList.add(Order.fromResultSet(rs));
            }
        } catch (SQLException e) {
            logger.error("Failed to search order list by sellercode. sellercode=" + selloercode, e);
        } finally {
            DbUtils.closeQuietly(con, ps, rs);
        }

        return orderList;
    }
}
