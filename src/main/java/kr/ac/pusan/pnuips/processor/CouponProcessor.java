package kr.ac.pusan.pnuips.processor;

import com.google.common.collect.Lists;
import kr.ac.pusan.pnuips.DatabaseManager;
import kr.ac.pusan.pnuips.model.coupon.Coupon;
import kr.ac.pusan.pnuips.model.coupon.CouponType;
import org.apache.commons.dbutils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class CouponProcessor {

    private static final Logger logger = LoggerFactory.getLogger(CouponProcessor.class);

    /**
     * 쿠폰을 검색한다
     *
     * @param type 쿠폰의 type
     * @return 쿠폰 객체
     */
    public CouponType searchCouponType(int type) {
        try {
            CouponType couponType = new CouponType(type);
            if (couponType.isExist()) {
                couponType.load();

                return couponType;
            }
        } catch (SQLException e) {
            logger.error("Failed to search coupon type. type=" + type, e);
        }
        return null;
    }

    /**
     * 사용자가 보유중인 쿠폰의 목록을 검색한다
     *
     * @param owener 사용자 email
     * @return 쿠폰 목록
     */
    public List<CouponType> searchCouponListByOwener(String owener) {
        logger.debug("Search coupon list by owener request. owener={}", owener);
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
            }
        } catch (SQLException e) {
            logger.error("Failed to search coupon list by owener. owener=" + owener, e);
        } finally {
            DbUtils.closeQuietly(con, ps, rs);
        }

        return couponList;
    }

    /**
     * 사용자의 쿠폰들을 제거한다
     *
     * @param owener  사용자 email
     * @param coupons 제거하고자 하는 쿠폰 타입의 집합
     * @throws SQLException 쿠폰 제거를 실패할 경우
     */
    public void deleteCoupons(String owener, Set<CouponType> coupons) throws SQLException {
        for (CouponType couponType : coupons) {
            Coupon coupon = new Coupon(couponType.getType(), owener);
            coupon.delete();
        }
    }
}
