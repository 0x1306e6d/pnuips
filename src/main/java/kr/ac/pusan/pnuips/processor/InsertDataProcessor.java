package kr.ac.pusan.pnuips.processor;

import kr.ac.pusan.pnuips.DatabaseConstants;
import kr.ac.pusan.pnuips.bean.InsertDataBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class InsertDataProcessor {

    private static final Logger logger = LoggerFactory.getLogger(InsertDataProcessor.class);

    public enum InsertDataResult {
        SUCCESS,
        SYSTEM_ERROR
    }

    public InsertDataResult insertAccountData(InsertDataBean.AccountData accountData) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            Class.forName(DatabaseConstants.DRIVER);
            con = DriverManager.getConnection(
                    DatabaseConstants.URL,
                    DatabaseConstants.USER,
                    DatabaseConstants.PASSWORD
            );

            ps = con.prepareStatement("INSERT INTO pnuips.account (email, password, firstname, lastname, birthday) VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, accountData.getEmail());
            ps.setString(2, accountData.getPassword());
            ps.setString(3, accountData.getFirstname());
            ps.setString(4, accountData.getLastname());
            ps.setDate(5, Date.valueOf(accountData.getBirth()));
            ps.executeUpdate();

            return InsertDataResult.SUCCESS;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
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
        return InsertDataResult.SYSTEM_ERROR;
    }

    public InsertDataResult insertItemData(InsertDataBean.ItemData itemData) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            Class.forName(DatabaseConstants.DRIVER);
            con = DriverManager.getConnection(
                    DatabaseConstants.URL,
                    DatabaseConstants.USER,
                    DatabaseConstants.PASSWORD
            );

            if (!hasSeller(con, itemData.getSellercode(), itemData.getSellername())) {
                insertNewSeller(con, itemData.getSellercode(), itemData.getSellername());
            }
            if (!hasItem(con, itemData.getItemcode())) {
                insertNewItem(con, itemData);
            }

            ps = con.prepareStatement("INSERT INTO pnuips.sell (itemcode, sellercode, price, numberOfStock, numberOfSales) VALUES (?, ?, ?, ?, ?)");
            ps.setInt(1, itemData.getItemcode());
            ps.setInt(2, itemData.getSellercode());
            ps.setInt(3, itemData.getPrice());
            ps.setInt(4, itemData.getNumberOfstock());
            ps.setInt(5, itemData.getNumberOfsales());
            ps.executeUpdate();

            return InsertDataResult.SUCCESS;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
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
        return InsertDataResult.SYSTEM_ERROR;
    }

    private boolean hasSeller(Connection con, int sellercode, String sellername) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement("SELECT sellercode, sellername FROM pnuips.seller WHERE sellercode=? AND sellername=?");
            ps.setInt(1, sellercode);
            ps.setString(2, sellername);
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

    private void insertNewSeller(Connection con, int sellercode, String sellername) throws SQLException {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("INSERT INTO pnuips.seller (sellercode, sellername) VALUES(?, ?)");
            ps.setInt(1, sellercode);
            ps.setString(2, sellername);
            ps.executeUpdate();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean hasItem(Connection con, int itemcode) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement("SELECT itemcode FROM pnuips.item WHERE itemcode=?");
            ps.setInt(1, itemcode);
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

    private void insertNewItem(Connection con, InsertDataBean.ItemData itemData) throws SQLException {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("INSERT INTO pnuips.item (itemcode, itemname, brand) VALUES (?, ?, ?)");
            ps.setInt(1, itemData.getItemcode());
            ps.setString(2, itemData.getItemname());
            ps.setString(3, itemData.getBrand());
            ps.executeUpdate();
        } finally {
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
