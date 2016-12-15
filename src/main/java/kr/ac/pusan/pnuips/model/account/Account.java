package kr.ac.pusan.pnuips.model.account;

import com.google.common.collect.Sets;
import kr.ac.pusan.pnuips.DatabaseManager;
import kr.ac.pusan.pnuips.model.Model;
import kr.ac.pusan.pnuips.model.coupon.Coupon;
import org.apache.commons.dbutils.DbUtils;

import java.sql.*;
import java.util.Set;

public class Account implements Model {

    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private Date birthday;
    private Grade grade;
    private final Set<Coupon> couponSet;

    public Account() {
        this.couponSet = Sets.newHashSet();
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public void addCoupon(Coupon coupon) {
        couponSet.add(coupon);
    }

    public Set<Coupon> getCouponSet() {
        return couponSet;
    }

    @Override
    public void insert() throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DatabaseManager.getConnection();
            ps = con.prepareStatement("INSERT INTO pnuips.account (email, password, firstname, lastname, birthday, grade) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setString(1, email);
            ps.setString(2, password);
            ps.setString(3, firstname);
            ps.setString(4, lastname);
            ps.setDate(5, birthday);
            ps.setInt(6, grade.getValue());
            ps.executeUpdate();
            // TODO : INSERT Coupon
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
            ps = con.prepareStatement("SELECT password, firstname, lastname, birthday, grade FROM pnuips.account WHERE email=?");
            ps.setString(1, email);
            rs = ps.executeQuery();

            if (rs.next()) {
                password = rs.getString("password");
                firstname = rs.getString("firstname");
                lastname = rs.getString("lastname");
                birthday = rs.getDate("birthday");
                grade = Grade.valueOf(rs.getInt("grade"));
            } else {
                throw new NullPointerException("Account is not exist. email=" + email);
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
            ps = con.prepareStatement("UPDATE pnuips.account SET password=?, firstname=?, lastname=?, birthday=?, grade=? WHERE email=?");
            ps.setString(1, password);
            ps.setString(2, firstname);
            ps.setString(3, lastname);
            ps.setDate(4, birthday);
            ps.setInt(5, grade.getValue());
            ps.executeUpdate();
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
            ps = con.prepareStatement("DELETE FROM pnuips.account WHERE email=?");
            ps.setString(1, email);
            ps.executeUpdate();
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
            ps = con.prepareStatement("SELECT email FROM pnuips.account WHERE email=?");
            ps.setString(1, email);
            rs = ps.executeQuery();

            return rs.next();
        } finally {
            DbUtils.closeQuietly(con, ps, rs);
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Account{");
        sb.append("email='").append(email).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", firstname='").append(firstname).append('\'');
        sb.append(", lastname='").append(lastname).append('\'');
        sb.append(", birthday=").append(birthday);
        sb.append(", grade=").append(grade);
        sb.append(", couponSet=").append(couponSet);
        sb.append('}');
        return sb.toString();
    }
}
