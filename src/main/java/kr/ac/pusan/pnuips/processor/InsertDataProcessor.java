package kr.ac.pusan.pnuips.processor;

import kr.ac.pusan.pnuips.DatabaseManager;
import kr.ac.pusan.pnuips.csv.ItemData;
import kr.ac.pusan.pnuips.csv.UserData;
import kr.ac.pusan.pnuips.model.account.Account;
import kr.ac.pusan.pnuips.model.cart.Cart;
import kr.ac.pusan.pnuips.model.coupon.CouponType;
import kr.ac.pusan.pnuips.model.item.Item;
import kr.ac.pusan.pnuips.model.order.Order;
import kr.ac.pusan.pnuips.model.sell.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InsertDataProcessor {

    public enum InsertDataResult {
        SUCCESS,
        SYSTEM_ERROR
    }

    public InsertDataResult insertAccountData(UserData userData) {
        Connection con = null;
        try {
            con = DatabaseManager.getConnection();

            for (CouponType couponType : userData.getCouponTypeSet()) {
                if (!hasCouponType(con, couponType)) {
                    couponType.insert();
                }
            }
            if (!hasAccount(con, userData.getAccount())) {
                userData.getAccount().insert();
            }

            return InsertDataResult.SUCCESS;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return InsertDataResult.SYSTEM_ERROR;
    }

    public InsertDataResult insertItemData(ItemData itemData) {
        Connection con = null;
        try {
            con = DatabaseManager.getConnection();

            if (!hasSeller(con, itemData.getSeller())) {
                itemData.getSeller().insert();
            }
            if (!hasItem(con, itemData.getItem())) {
                itemData.getItem().insert();
            }
            itemData.getSell().insert();

            for (Cart cart : itemData.getCartSet()) {
                cart.insert();
            }
            for (Order order : itemData.getOrderSet()) {
                order.insert();
            }

            return InsertDataResult.SUCCESS;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return InsertDataResult.SYSTEM_ERROR;
    }

    private boolean hasAccount(Connection con, Account account) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement("SELECT email FROM pnuips.account WHERE email=?");
            ps.setString(1, account.getEmail());
            rs = ps.executeQuery();
            return rs.next();
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
        }
    }

    private boolean hasCouponType(Connection con, CouponType couponType) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement("SELECT type FROM pnuips.couponType WHERE type=?");
            ps.setInt(1, couponType.getType());
            rs = ps.executeQuery();
            return rs.next();
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
        }
    }

    private boolean hasSeller(Connection con, Seller seller) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement("SELECT sellercode, sellername FROM pnuips.seller WHERE sellercode=? AND sellername=?");
            ps.setInt(1, seller.getSellercode());
            ps.setString(2, seller.getSellername());
            rs = ps.executeQuery();
            return rs.next();
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
        }
    }

    private boolean hasItem(Connection con, Item item) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement("SELECT itemcode FROM pnuips.item WHERE itemcode=?");
            ps.setInt(1, item.getItemcode());
            rs = ps.executeQuery();
            return rs.next();
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
        }
    }
}
