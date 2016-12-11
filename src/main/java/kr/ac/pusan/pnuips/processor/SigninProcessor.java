package kr.ac.pusan.pnuips.processor;

import kr.ac.pusan.pnuips.DatabaseConstants;
import kr.ac.pusan.pnuips.bean.SigninBean;
import org.apache.commons.lang3.StringUtils;

import java.sql.*;

public class SigninProcessor {

    public enum SigninResult {
        SUCCESS,
        INVALID_PASSWORD,
        NO_EXISTS_ACCOUNT,
        SYSTEM_ERROR
    }

    public SigninResult signin(SigninBean bean) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            Class.forName(DatabaseConstants.DRIVER);
            con = DriverManager.getConnection(
                    DatabaseConstants.URL,
                    DatabaseConstants.USER,
                    DatabaseConstants.PASSWORD
            );
            ps = con.prepareStatement("SELECT email, password FROM pnuips.account WHERE email=?");
            ps.setString(1, bean.getEmail());
            rs = ps.executeQuery();

            if (rs.next()) {
                String password = rs.getString("password");

                if (StringUtils.equals(bean.getPassword(), password)) {
                    return SigninResult.SUCCESS;
                } else {
                    return SigninResult.INVALID_PASSWORD;
                }
            } else {
                return SigninResult.NO_EXISTS_ACCOUNT;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
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
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return SigninResult.SYSTEM_ERROR;
    }
}
