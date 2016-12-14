package kr.ac.pusan.pnuips.processor;

import com.google.common.collect.Lists;
import kr.ac.pusan.pnuips.DatabaseManager;
import kr.ac.pusan.pnuips.model.coupon.CouponType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CouponProcessor {

    public List<CouponType> searchCouponList(String owener) {
        List<CouponType> couponList = Lists.newArrayList();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DatabaseManager.getConnection();
            ps = con.prepareStatement("SELECT type, name, discount FROM pnuips.couponType WHERE type IN (SELECT type FROM pnuips.coupon WHERE owener=?);");
            ps.setString(1, owener);
            rs = ps.executeQuery();

            while (rs.next()) {
                CouponType couponType = new CouponType();
                couponType.setType(rs.getInt("type"));
                couponType.setName(rs.getString("name"));
                couponType.setDiscount(rs.getInt("discount"));

                couponList.add(couponType);
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

        return couponList;
    }
}
