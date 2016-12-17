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
}
