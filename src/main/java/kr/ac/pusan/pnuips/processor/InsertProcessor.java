package kr.ac.pusan.pnuips.processor;

import kr.ac.pusan.pnuips.DatabaseManager;
import kr.ac.pusan.pnuips.csv.ItemData;
import kr.ac.pusan.pnuips.csv.UserData;
import kr.ac.pusan.pnuips.model.account.Account;
import kr.ac.pusan.pnuips.model.cart.Cart;
import kr.ac.pusan.pnuips.model.coupon.CouponType;
import kr.ac.pusan.pnuips.model.item.Item;
import kr.ac.pusan.pnuips.model.order.Order;
import kr.ac.pusan.pnuips.model.sell.Sell;
import kr.ac.pusan.pnuips.model.sell.Seller;
import org.apache.commons.dbutils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertProcessor {

    private static final Logger logger = LoggerFactory.getLogger(InsertProcessor.class);

    public enum InsertProcessorResult {
        SUCCESS,
        SYSTEM_ERROR
    }

    public InsertProcessorResult clearDatabase() {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DatabaseManager.getConnection();
            ps = con.prepareStatement("DELETE FROM pnuips.account, pnuips.item, pnuips.seller, pnuips.sell, pnuips.cart, pnuips.order, pnuips.couponType, pnuips.coupon");
            ps.executeUpdate();

            return InsertProcessorResult.SUCCESS;
        } catch (SQLException e) {
            logger.error("Failed to clear database.", e);
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(con);
        }

        return InsertProcessorResult.SYSTEM_ERROR;
    }

    public InsertProcessorResult insertAccountData(UserData userData) {
        for (CouponType couponType : userData.getCouponTypeSet()) {
            try {
                if (!couponType.isExist()) {
                    couponType.insert();
                }
            } catch (SQLException e) {
                logger.error("Failed to insert CouponType. couponType=" + couponType, e);
                return InsertProcessorResult.SYSTEM_ERROR;
            }
        }

        Account account = userData.getAccount();
        try {
            if (!account.isExist()) {
                account.insert();
            }
        } catch (SQLException e) {
            logger.error("Failed to insert Account. account=" + account, e);
            return InsertProcessorResult.SYSTEM_ERROR;
        }
        logger.info("Insert user data. userData={}", userData);

        return InsertProcessorResult.SUCCESS;
    }

    public InsertProcessorResult insertItemData(ItemData itemData) {
        Item item = itemData.getItem();
        try {
            if (!item.isExist()) {
                item.insert();
            }
        } catch (SQLException e) {
            logger.error("Failed to insert Item. item=" + item, e);
            return InsertProcessorResult.SYSTEM_ERROR;
        }

        Seller seller = itemData.getSeller();
        try {
            if (!seller.isExist()) {
                seller.insert();
            }
        } catch (SQLException e) {
            logger.error("Failed to insert Seller. seller=" + seller, e);
            return InsertProcessorResult.SYSTEM_ERROR;
        }

        Sell sell = itemData.getSell();
        try {
            if (!sell.isExist()) {
                sell.insert();
            }
        } catch (SQLException e) {
            logger.error("Failed to insert Sell. sell=" + sell, e);
            return InsertProcessorResult.SYSTEM_ERROR;
        }

        for (Cart cart : itemData.getCartSet()) {
            try {
                if (!cart.isExist()) {
                    cart.insert();
                }
            } catch (SQLException e) {
                logger.error("Failed to insert Cart. cart=" + cart, e);
                return InsertProcessorResult.SYSTEM_ERROR;
            }
        }

        for (Order order : itemData.getOrderSet()) {
            try {
                if (!order.isExist()) {
                    order.insert();
                }
            } catch (SQLException e) {
                logger.error("Failed to insert Order. order=" + order, e);
                return InsertProcessorResult.SYSTEM_ERROR;
            }
        }
        logger.info("Insert item data. itemData={}", itemData);

        return InsertProcessorResult.SUCCESS;
    }
}
