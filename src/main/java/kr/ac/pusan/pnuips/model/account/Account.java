package kr.ac.pusan.pnuips.model.account;

import com.google.common.collect.Lists;
import kr.ac.pusan.pnuips.DatabaseManager;
import kr.ac.pusan.pnuips.model.coupon.Coupon;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class Account {

    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private Date birth;
    private final List<Coupon> couponList;

    public Account() {
        this.couponList = Lists.newArrayList();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public void addCoupon(Coupon coupon) {
        couponList.add(coupon);
    }

    public List<Coupon> getCouponList() {
        return couponList;
    }

    public void insert() throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DatabaseManager.getConnection();
            try {
                ps = con.prepareStatement("INSERT INTO pnuips.account (email, password, firstname, lastname, birthday) VALUES (?, ?, ?, ?, ?)");
                ps.setString(1, email);
                ps.setString(2, password);
                ps.setString(3, firstname);
                ps.setString(4, lastname);
                ps.setDate(5, birth);
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

            for (Coupon coupon : couponList) {
                try {
                    ps = con.prepareStatement("INSERT INTO pnuips.coupon (type, owener) VALUES (?, ?)");
                    ps.setInt(1, coupon.getType());
                    ps.setString(2, coupon.getOwener());
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
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Account{");
        sb.append("email='").append(email).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", firstname='").append(firstname).append('\'');
        sb.append(", lastname='").append(lastname).append('\'');
        sb.append(", birth=").append(birth);
        sb.append(", couponList=").append(couponList);
        sb.append('}');
        return sb.toString();
    }
}
