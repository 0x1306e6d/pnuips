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
                Order order = new Order();
                order.setItemcode(rs.getInt("itemcode"));
                order.setSellercode(rs.getInt("sellercode"));
                order.setPurchaser(rs.getString("purchaser"));
                order.setCount(rs.getInt("count"));
                order.setDiscount(rs.getInt("discount"));
                order.setTime(rs.getTimestamp("time"));

                orderList.add(order);
                logger.debug("Add order : {}", order);
            }
        } catch (SQLException e) {
            logger.error("Failed to search ordr list. purchaser=" + purchaser, e);
        } finally {
            DbUtils.closeQuietly(con, ps, rs);
        }

        return orderList;
    }
}
