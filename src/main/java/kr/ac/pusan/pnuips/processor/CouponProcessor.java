package kr.ac.pusan.pnuips.processor;

import com.google.common.collect.Lists;
import kr.ac.pusan.pnuips.DatabaseManager;
import kr.ac.pusan.pnuips.model.coupon.CouponType;
import org.apache.commons.dbutils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CouponProcessor {

    private static final Logger logger = LoggerFactory.getLogger(CouponProcessor.class);

    public List<CouponType> searchCouponList(String owener) {
        List<CouponType> couponList = Lists.newArrayList();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DatabaseManager.getConnection();
            ps = con.prepareStatement("SELECT type FROM pnuips.coupon WHERE owener=?");
            ps.setString(1, owener);
            rs = ps.executeQuery();

            while (rs.next()) {
                CouponType couponType = new CouponType(rs.getInt("type"));
                couponType.load();

                couponList.add(couponType);
                logger.debug("Add coupon : {}", couponType);
            }
        } catch (SQLException e) {
            logger.error("Failed to search coupon list. owener=" + owener, e);
        } finally {
            DbUtils.closeQuietly(con, ps, rs);
        }

        return couponList;
    }

    public int getDiscount(int couponType) {
        int discount = 0;

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DatabaseManager.getConnection();
            ps = con.prepareStatement("SELECT discount FROM pnuips.couponType WHERE type=?");
            ps.setInt(1, couponType);
            rs = ps.executeQuery();

            if (rs.next()) {
                discount = rs.getInt("discount");
            }
        } catch (SQLException e) {
            logger.error("Failed to get discount. type=" + couponType, e);
        } finally {
            DbUtils.closeQuietly(con, ps, rs);
        }

        return discount;
    }
}
