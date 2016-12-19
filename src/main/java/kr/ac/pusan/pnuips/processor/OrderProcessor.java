package kr.ac.pusan.pnuips.processor;

import com.google.common.collect.Lists;
import kr.ac.pusan.pnuips.DatabaseManager;
import kr.ac.pusan.pnuips.model.order.Order;
import org.apache.commons.dbutils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.List;

public class OrderProcessor {

    private static final Logger logger = LoggerFactory.getLogger(OrderProcessor.class);

    public List<Order> searchOrderList(String purchaser) {
        List<Order> orderList = Lists.newArrayList();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DatabaseManager.getConnection();
            ps = con.prepareStatement("SELECT itemcode, sellercode, purchaser, count, discount, time FROM pnuips.order WHERE purchaser=?");
            ps.setString(1, purchaser);
            rs = ps.executeQuery();

            while (rs.next()) {
                Order order = getOrderFromResultSet(rs);
                orderList.add(order);
            }
        } catch (SQLException e) {
            logger.error("Failed to search order list by purchaser. purchaser=" + purchaser, e);
        } finally {
            DbUtils.closeQuietly(con, ps, rs);
        }

        return orderList;
    }

    public List<Order> searchOrderListBySellercode(int selloercode) {
        List<Order> orderList = Lists.newArrayList();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DatabaseManager.getConnection();
            ps = con.prepareStatement("SELECT itemcode, sellercode, purchaser, count, discount, time FROM pnuips.order WHERE sellercode=?");
            ps.setInt(1, selloercode);
            rs = ps.executeQuery();

            while (rs.next()) {
                Order order = getOrderFromResultSet(rs);
                orderList.add(order);
            }
        } catch (SQLException e) {
            logger.error("Failed to search order list by sellercode. sellercode=" + selloercode, e);
        } finally {
            DbUtils.closeQuietly(con, ps, rs);
        }

        return orderList;
    }

    public Order purchase(int itemcode, int sellercode, String purchaser, int count, int discount) {
        try {
            Order order = new Order(itemcode, sellercode, purchaser);
            order.setCount(count);
            order.setDiscount(discount);
            order.setTime(new Timestamp(System.currentTimeMillis()));
            order.insert();

            return order;
        } catch (SQLException e) {
            logger.error("Failed to purchase. itemcode=" + itemcode + ", sellercode=" + sellercode + ", purchaser=" + purchaser + ", count=" + count + ", discount=" + discount, e);
        }
        return null;
    }

    private Order getOrderFromResultSet(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setItemcode(rs.getInt("itemcode"));
        order.setSellercode(rs.getInt("sellercode"));
        order.setPurchaser(rs.getString("purchaser"));
        order.setCount(rs.getInt("count"));
        order.setDiscount(rs.getInt("discount"));
        order.setTime(rs.getTimestamp("time"));
        return order;
    }
}
