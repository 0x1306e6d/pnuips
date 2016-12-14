package kr.ac.pusan.pnuips.processor;

import com.google.common.collect.Lists;
import kr.ac.pusan.pnuips.DatabaseManager;
import kr.ac.pusan.pnuips.model.order.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderProcessor {

    public List<Order> searchOrderList(String purchaser) {
        List<Order> orderList = Lists.newArrayList();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DatabaseManager.getConnection();
            ps = con.prepareStatement("SELECT itemcode, purchaser, ordercount, discount, ordertime FROM pnuips.order WHERE purchaser=?");
            ps.setString(1, purchaser);
            rs = ps.executeQuery();

            while (rs.next()) {
                Order order = new Order();
                order.setItemcode(rs.getInt("itemcode"));
                order.setPurchaser(rs.getString("purchaser"));
                order.setCount(rs.getInt("ordercount"));
                order.setDiscount(rs.getInt("discount"));
                order.setTime(rs.getTimestamp("ordertime"));

                orderList.add(order);
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

        return orderList;
    }
}
