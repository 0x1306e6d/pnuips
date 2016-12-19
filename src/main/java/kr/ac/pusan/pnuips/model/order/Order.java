package kr.ac.pusan.pnuips.model.order;

import kr.ac.pusan.pnuips.DatabaseManager;
import kr.ac.pusan.pnuips.model.Model;
import org.apache.commons.dbutils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class Order implements Model {

    private static final Logger logger = LoggerFactory.getLogger(Order.class);

    private int itemcode;
    private int sellercode;
    private String purchaser;
    private int count;
    private int discount;
    private Timestamp time;

    public Order() {
    }

    public Order(int itemcode, int sellercode, String purchaser) {
        this.itemcode = itemcode;
        this.sellercode = sellercode;
        this.purchaser = purchaser;
    }

    public int getItemcode() {
        return itemcode;
    }

    public void setItemcode(int itemcode) {
        this.itemcode = itemcode;
    }

    public int getSellercode() {
        return sellercode;
    }

    public void setSellercode(int sellercode) {
        this.sellercode = sellercode;
    }

    public String getPurchaser() {
        return purchaser;
    }

    public void setPurchaser(String purchaser) {
        this.purchaser = purchaser;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public void insert() throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DatabaseManager.getConnection();
            ps = con.prepareStatement("INSERT INTO pnuips.order (itemcode, sellercode, purchaser, count, discount, time) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setInt(1, itemcode);
            ps.setInt(2, sellercode);
            ps.setString(3, purchaser);
            ps.setInt(4, count);
            ps.setInt(5, discount);
            ps.setTimestamp(6, time);
            ps.executeUpdate();

            logger.trace("Insert order. {}", this);
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(con);
        }
    }

    @Override
    public void load() throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DatabaseManager.getConnection();
            ps = con.prepareStatement("SELECT count, discount, time FROM pnuips.order WHERE itemcode=? AND sellercode=? AND purchaser=?");
            ps.setInt(1, itemcode);
            ps.setInt(2, sellercode);
            ps.setString(3, purchaser);
            rs = ps.executeQuery();

            if (rs.next()) {
                count = rs.getInt("count");
                discount = rs.getInt("discount");
                time = rs.getTimestamp("time");

                logger.trace("Load order. {}", this);
            } else {
                throw new NullPointerException("Order is not exist. itemcode=" + itemcode + ", sellercode=" + sellercode + ", purchaser=" + purchaser);
            }
        } finally {
            DbUtils.closeQuietly(con, ps, rs);
        }
    }

    @Override
    public void update() throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DatabaseManager.getConnection();
            ps = con.prepareStatement("UPDATE pnuips.order SET count=?, discount=?, time=? WHERE itemcode=? AND sellercode=? AND purchaser=?");
            ps.setInt(1, count);
            ps.setInt(2, discount);
            ps.setTimestamp(3, time);
            ps.setInt(4, itemcode);
            ps.setInt(5, sellercode);
            ps.setString(6, purchaser);
            ps.executeUpdate();

            logger.trace("Update order. {}", this);
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(con);
        }
    }

    @Override
    public void delete() throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DatabaseManager.getConnection();
            ps = con.prepareStatement("DELETE FROM pnuips.order WHERE itemcode=? AND sellercode=? AND purchaser=?");
            ps.setInt(1, itemcode);
            ps.setInt(2, sellercode);
            ps.setString(3, purchaser);
            ps.executeUpdate();

            logger.trace("Delete order. {}", this);
        } finally {
            DbUtils.closeQuietly(ps);
            DbUtils.closeQuietly(con);
        }
    }

    @Override
    public boolean isExist() throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DatabaseManager.getConnection();
            ps = con.prepareStatement("SELECT itemcode, sellercode, purchaser FROM pnuips.order WHERE itemcode=? AND sellercode=? AND purchaser=?");
            ps.setInt(1, itemcode);
            ps.setInt(2, sellercode);
            ps.setString(3, purchaser);
            rs = ps.executeQuery();

            return rs.next();
        } finally {
            DbUtils.closeQuietly(con, ps, rs);
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");
        sb.append("itemcode=").append(itemcode);
        sb.append(", sellercode=").append(sellercode);
        sb.append(", purchaser='").append(purchaser).append('\'');
        sb.append(", count=").append(count);
        sb.append(", discount=").append(discount);
        sb.append(", time=").append(time);
        sb.append('}');
        return sb.toString();
    }
}
