package kr.ac.pusan.pnuips.processor;

import com.google.common.collect.Lists;
import kr.ac.pusan.pnuips.DatabaseManager;
import kr.ac.pusan.pnuips.model.cart.Cart;
import org.apache.commons.dbutils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CartProcessor {

    private static final Logger logger = LoggerFactory.getLogger(CartProcessor.class);

    /**
     * 사용자의 장바구니 목록을 구한다.
     *
     * @param owener 사용자의 email
     * @return 장바구니 목록
     */
    public List<Cart> searchCartListByOwener(String owener) {
        List<Cart> cartList = Lists.newArrayList();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DatabaseManager.getConnection();
            ps = con.prepareStatement("SELECT itemcode, sellercode, owener, count FROM pnuips.cart WHERE owener=?");
            ps.setString(1, owener);
            rs = ps.executeQuery();

            while (rs.next()) {
                Cart cart = new Cart();
                cart.setItemcode(rs.getInt("itemcode"));
                cart.setSellercode(rs.getInt("sellercode"));
                cart.setOwener(rs.getString("owener"));
                cart.setCount(rs.getInt("count"));

                cartList.add(cart);
            }
        } catch (SQLException e) {
            logger.error("Failed to search cart list. owener=" + owener, e);
        } finally {
            DbUtils.closeQuietly(con, ps, rs);
        }

        return cartList;
    }

    /**
     * 사용자가 장바구니에 담은 상품의 총 개수를 구한다.
     *
     * @param itemcode   상품의 itemcode
     * @param sellercode 상품 판매자의 sellercode
     * @return 총 개수
     */
    public int getTotalCartCount(int itemcode, int sellercode) {
        int count = 0;

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DatabaseManager.getConnection();
            ps = con.prepareStatement("SELECT SUM(count) FROM pnuips.cart WHERE itemcode=? AND sellercode=?");
            ps.setInt(1, itemcode);
            ps.setInt(2, sellercode);
            rs = ps.executeQuery();

            while (rs.next()) {
                count += rs.getInt(1);
            }
        } catch (SQLException e) {
            logger.error("Failed to get total cart count. itemcode=" + itemcode + ", sellercode=" + sellercode, e);
        } finally {
            DbUtils.closeQuietly(con, ps, rs);
        }

        return count;
    }

    public Cart addCart(int itemcode, int sellercode, String owener, int count) {
        try {
            Cart cart = new Cart(itemcode, sellercode, owener);

            if (cart.isExist()) {
                cart.load();
                cart.setCount(cart.getCount() + count);
                cart.update();
            } else {
                cart.setCount(count);
                cart.insert();
            }

            return cart;
        } catch (SQLException e) {
            logger.error("Failed to insert cart. itemcode=" + itemcode + ", sellercode=" + sellercode + ", owener=" + owener + ", count=" + count, e);
        }
        return null;
    }
}
