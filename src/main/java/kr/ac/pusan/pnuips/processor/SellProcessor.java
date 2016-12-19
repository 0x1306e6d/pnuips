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

    /**
     * 주어진 판매자가 판매하지 않는 상품 중 BEST 10 을 검색한다.
     *
     * @param target 제외하고자 하는 판매자 sellercode
     * @return BEST 10 리스트
     */
    public List<SellBean> searchSellBeanListWithoutSeller(int target) {
        List<SellBean> sellBeanList = Lists.newArrayList();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DatabaseManager.getConnection();
            ps = con.prepareStatement("SELECT itemcode, sellercode FROM pnuips.order NATURAL JOIN pnuips.sell WHERE sellercode<>? GROUP BY itemcode, sellercode ORDER BY SUM(price * count * (100-discount)/100) DESC LIMIT 10");
            ps.setInt(1, target);
            rs = ps.executeQuery();

            while (rs.next()) {
                int itemcode = rs.getInt("itemcode");
                int sellercode = rs.getInt("sellercode");

                SellBean sellBean = searchSellBean(itemcode, sellercode);
                sellBeanList.add(sellBean);
            }
        } catch (SQLException e) {
            logger.error("Failed to search sell bean list without seller. sellercode=" + target, e);
        } finally {
            DbUtils.closeQuietly(con, ps, rs);
        }

        return sellBeanList;
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

    /**
     * 장바구니의 총 합보다 재고가 작은 상품의 목록을 찾는다.
     *
     * @return 상품 목록
     */
    public List<SellBean> searchSellBeanWithSoldOutRisk() {
        List<SellBean> sellBeanList = Lists.newArrayList();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DatabaseManager.getConnection();
            ps = con.prepareStatement("SELECT * FROM ((pnuips.sell NATURAL JOIN pnuips.seller) NATURAL JOIN pnuips.item) sellbean WHERE numberOfStock < (SELECT SUM(count) FROM pnuips.cart WHERE itemcode=sellbean.itemcode AND sellercode=sellbean.sellercode)");
            rs = ps.executeQuery();

            while (rs.next()) {
                SellBean sellBean = getSellBeanFromResultSet(rs);
                sellBeanList.add(sellBean);
            }
        } catch (SQLException e) {
            logger.error("Failed to search sold out risk sells.", e);
        } finally {
            DbUtils.closeQuietly(con, ps, rs);
        }

        return sellBeanList;
    }

    public void increaseSalesCount(int itemcode, int sellercode, int count) throws SQLException {
        Sell sell = new Sell(itemcode, sellercode);
        sell.load();

        sell.setNumberOfSales(sell.getNumberOfSales() + count);
        sell.setNumberOfStock(sell.getNumberOfStock() - count);
        sell.update();
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