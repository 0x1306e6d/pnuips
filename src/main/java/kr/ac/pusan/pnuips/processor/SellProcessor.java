package kr.ac.pusan.pnuips.processor;

import com.google.common.collect.Lists;
import kr.ac.pusan.pnuips.DatabaseManager;
import kr.ac.pusan.pnuips.bean.SellBean;
import kr.ac.pusan.pnuips.model.item.Item;
import kr.ac.pusan.pnuips.model.sell.Sell;
import kr.ac.pusan.pnuips.model.sell.Seller;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.List;

public class SellProcessor {

    private static final Logger logger = LoggerFactory.getLogger(SellProcessor.class);

    public SellBean searchSellBean(int itemcode, int sellercode) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DatabaseManager.getConnection();
            ps = con.prepareStatement("SELECT * FROM (pnuips.sell NATURAL JOIN pnuips.seller) NATURAL JOIN pnuips.item WHERE itemcode=? AND sellercode=?");
            ps.setInt(1, itemcode);
            ps.setInt(2, sellercode);
            rs = ps.executeQuery();

            if (rs.next()) {
                return getSellBeanFromResultSet(rs);
            }
        } catch (SQLException e) {
            logger.error("Failed to search sell bean. itemcode=" + itemcode + ", sellercode=" + sellercode, e);
        } finally {
            DbUtils.closeQuietly(con, ps, rs);
        }

        return null;
    }

    public List<SellBean> searchSellBeanListOfSeller(int sellercode) {
        List<SellBean> sellBeanList = Lists.newArrayList();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DatabaseManager.getConnection();
            ps = con.prepareStatement("SELECT * FROM (pnuips.sell NATURAL JOIN pnuips.seller) NATURAL JOIN pnuips.item WHERE sellercode=?");
            ps.setInt(1, sellercode);
            rs = ps.executeQuery();

            while (rs.next()) {
                SellBean sellBean = getSellBeanFromResultSet(rs);
                sellBeanList.add(sellBean);
            }
        } catch (SQLException e) {
            logger.error("Failed to search sell bean list of seller. sellercode=" + sellercode, e);
        } finally {
            DbUtils.closeQuietly(con, ps, rs);
        }

        return sellBeanList;
    }

    public List<SellBean> searchSellBeanListByName(String itemname) {
        List<SellBean> sellBeanList = Lists.newArrayList();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DatabaseManager.getConnection();
            ps = con.prepareStatement("SELECT * FROM (pnuips.sell NATURAL JOIN pnuips.seller) NATURAL JOIN pnuips.item WHERE itemname=?");
            ps.setString(1, itemname);
            rs = ps.executeQuery();

            while (rs.next()) {
                SellBean sellBean = getSellBeanFromResultSet(rs);
                sellBeanList.add(sellBean);
            }
        } catch (SQLException e) {
            logger.error("Failed to search sell list by itemname. itemname=" + itemname, e);
        } finally {
            DbUtils.closeQuietly(con, ps, rs);
        }

        return sellBeanList;
    }

    public List<SellBean> searchSellBeanListBySimilarName(String itemname) {
        List<SellBean> sellBeanList = Lists.newArrayList();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DatabaseManager.getConnection();
            ps = con.prepareStatement("SELECT * FROM (pnuips.sell NATURAL JOIN pnuips.seller) NATURAL JOIN pnuips.item WHERE itemname <> ? AND itemname LIKE ?");
            ps.setString(1, itemname);
            ps.setString(2, "%" + itemname + "%");
            rs = ps.executeQuery();

            while (rs.next()) {
                SellBean sellBean = getSellBeanFromResultSet(rs);
                sellBeanList.add(sellBean);
            }
        } catch (SQLException e) {
            logger.error("Failed to search sell list by similar itemname. itemname=" + itemname, e);
        } finally {
            DbUtils.closeQuietly(con, ps, rs);
        }

        return sellBeanList;
    }

    public List<SellBean> searchSellBeanList(int start) {
        List<SellBean> sellBeanList = Lists.newArrayList();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DatabaseManager.getConnection();
            ps = con.prepareStatement("SELECT * FROM (pnuips.sell NATURAL JOIN pnuips.seller) NATURAL JOIN pnuips.item LIMIT 20 OFFSET " + start);
            rs = ps.executeQuery();

            while (rs.next()) {
                SellBean sellBean = getSellBeanFromResultSet(rs);
                sellBeanList.add(sellBean);
            }
        } catch (SQLException e) {
            logger.error("Failed to search sell bean list. start=" + start, e);
        } finally {
            DbUtils.closeQuietly(con, ps, rs);
        }

        return sellBeanList;
    }

    public List<SellBean> searchBestSellBeanList(int limit) {
        List<SellBean> sellBeanList = Lists.newArrayList();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DatabaseManager.getConnection();
            ps = con.prepareStatement("SELECT * FROM (pnuips.sell NATURAL JOIN pnuips.seller) NATURAL JOIN pnuips.item ORDER BY numberOfSales DESC LIMIT " + limit);
            rs = ps.executeQuery();

            while (rs.next()) {
                SellBean sellBean = getSellBeanFromResultSet(rs);
                sellBeanList.add(sellBean);
            }
        } catch (SQLException e) {
            logger.error("Failed to search best sell bean list.", e);
        } finally {
            DbUtils.closeQuietly(con, ps, rs);
        }

        return sellBeanList;
    }

    public List<SellBean> searchBestSellBeanListBetweenTime(String start, String end) {
        if (StringUtils.isEmpty(start) || StringUtils.isEmpty(end)) {
            return searchBestSellBeanList(3);
        }
        List<SellBean> sellBeanList = Lists.newArrayList();
        start = start.replace('T', ' ');
        end = end.replace('T', ' ');
        logger.debug("Search bestseller between time. start={}, end={}", start, end);

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DatabaseManager.getConnection();
            ps = con.prepareStatement("SELECT itemcode, sellercode, SUM(count) FROM pnuips.order NATURAL JOIN pnuips.item WHERE time > ? AND time < ? GROUP BY itemcode, sellercode ORDER BY sum DESC LIMIT 3");
            ps.setTimestamp(1, Timestamp.valueOf(start));
            ps.setTimestamp(2, Timestamp.valueOf(end));
            rs = ps.executeQuery();

            while (rs.next()) {
                int itemcode = rs.getInt("itemcode");
                int sellercode = rs.getInt("sellercode");

                SellBean sellBean = searchSellBean(itemcode, sellercode);
                sellBeanList.add(sellBean);
            }
        } catch (SQLException e) {
            logger.error("Failed to search best sell bean list between time. start=" + start + ", end=" + end, e);
        } finally {
            DbUtils.closeQuietly(con, ps, rs);
        }

        return sellBeanList;
    }

    private SellBean getSellBeanFromResultSet(ResultSet rs) throws SQLException {
        SellBean sellBean = new SellBean();

        Item item = new Item();
        item.setItemcode(rs.getInt("itemcode"));
        item.setItemname(rs.getString("itemname"));
        item.setBrand(rs.getString("brand"));
        sellBean.setItem(item);

        Seller seller = new Seller();
        seller.setSellercode(rs.getInt("sellercode"));
        seller.setSellername(rs.getString("sellername"));
        sellBean.setSeller(seller);

        Sell sell = new Sell();
        sell.setItemcode(rs.getInt("itemcode"));
        sell.setSellercode(rs.getInt("sellercode"));
        sell.setPrice(rs.getInt("price"));
        sell.setNumberOfStock(rs.getInt("numberOfStock"));
        sell.setNumberOfSales(rs.getInt("numberOfSales"));
        sellBean.setSell(sell);

        return sellBean;
    }
}
