package kr.ac.pusan.pnuips.csv;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import kr.ac.pusan.pnuips.model.account.Account;
import kr.ac.pusan.pnuips.model.coupon.Coupon;
import kr.ac.pusan.pnuips.model.coupon.CouponType;
import org.apache.commons.lang3.StringUtils;

import java.sql.Date;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class UserData {

    public static List<UserData> fromCsv(String csv) {
        List<UserData> userDataList = Lists.newArrayList();
        if (StringUtils.isEmpty(csv)) {
            return userDataList;
        }

        String[] rows = csv.split("\r\n");
        for (int i = 1; i < rows.length; ++i) { // first line is name
            String row = rows[i];
            String[] data = row.split(",");

            UserData userData = new UserData();

            Account account = new Account();
            account.setEmail(data[0]);
            account.setPassword(data[1]);
            account.setFirstname(data[2]);
            account.setLastname(data[3]);
            account.setBirth(Date.valueOf(data[4]));

            if (data.length > 5) {
                Set<CouponType> couponTypeSet = getCouponTypeSet(data[5]);
                for (CouponType couponType : couponTypeSet) {
                    userData.addCouponType(couponType);

                    Coupon coupon = new Coupon();
                    coupon.setType(couponType.getType());
                    coupon.setOwener(account.getEmail());

                    account.addCoupon(coupon);
                }
            }

            userData.setAccount(account);

            userDataList.add(userData);
        }

        return userDataList;
    }

    private static Set<CouponType> getCouponTypeSet(String csv) {
        Set<CouponType> couponTypeSet = Sets.newHashSet();
        if (StringUtils.isEmpty(csv)) {
            return couponTypeSet;
        }

        String[] rows = csv.split(Pattern.quote("|"));
        for (String row : rows) {
            String[] data = row.split(" ");

            CouponType couponType = new CouponType();
            couponType.setType(Integer.parseInt(data[0]));
            couponType.setName(data[1]);
            couponType.setDiscount(Integer.parseInt(data[2]));

            couponTypeSet.add(couponType);
        }
        return couponTypeSet;
    }

    private Account account;
    private final Set<CouponType> couponTypeSet;

    public UserData() {
        this.couponTypeSet = Sets.newHashSet();
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void addCouponType(CouponType couponType) {
        if (!couponTypeSet.contains(couponType)) {
            couponTypeSet.add(couponType);
        }
    }

    public Set<CouponType> getCouponTypeSet() {
        return couponTypeSet;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserData{");
        sb.append("account=").append(account);
        sb.append(", couponTypeSet=").append(couponTypeSet);
        sb.append('}');
        return sb.toString();
    }
}
