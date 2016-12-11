package kr.ac.pusan.pnuips.processor;

import kr.ac.pusan.pnuips.DatabaseConstants;
import kr.ac.pusan.pnuips.bean.SignupBean;

import java.sql.*;

public class SignupProcessor {

    public enum SignupResult {
        SUCCESS,
        ALREADY_EXISTS_ACCOUNT,
        SYSTEM_ERROR
    }

    public SignupResult signup(SignupBean bean) {
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
            // 이미 존재하는 이메일 주소 체크
            try {
                ps = con.prepareStatement("SELECT email FROM pnuips.account WHERE email=?");
                ps.setString(1, bean.getEmail());
                rs = ps.executeQuery();
                if (rs.next()) {
                    return SignupResult.ALREADY_EXISTS_ACCOUNT;
                }
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }

            ps = con.prepareStatement("INSERT INTO pnuips.account (email, password, firstname, lastname, birthday) VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, bean.getEmail());
            ps.setString(2, bean.getPassword());
            ps.setString(3, bean.getFirstname());
            ps.setString(4, bean.getLastname());
            ps.setDate(5, Date.valueOf(bean.getBirthday()));
            ps.execute();
            return SignupResult.SUCCESS;
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
        return SignupResult.SYSTEM_ERROR;
    }
}
