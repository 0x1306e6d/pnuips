package kr.ac.pusan.pnuips.bean;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class InsertDataBean {

    private String userData;
    private String itemData;

    public String getUserData() {
        return userData;
    }

    public void setUserData(String userData) {
        this.userData = userData;
    }

    public String getItemData() {
        return itemData;
    }

    public void setItemData(String itemData) {
        this.itemData = itemData;
    }

    public List<AccountData> csvToAccountData() {
        List<AccountData> accountDataList = Lists.newArrayList();
        if (StringUtils.isEmpty(userData)) {
            return accountDataList;
        }

        String[] rows = userData.split("\r\n");
        for (int i = 1; i < rows.length; ++i) { // first line is name
            String row = rows[i];
            String[] data = row.split(",");

            AccountData accountData = new AccountData();
            accountData.email = data[0];
            accountData.password = data[1];
            accountData.firstname = data[2];
            accountData.lastname = data[3];
            accountData.birth = data[4];
            accountDataList.add(accountData);
        }

        return accountDataList;
    }

    public List<ItemData> csvToItemData() {
        List<ItemData> itemDataList = Lists.newArrayList();
        if (StringUtils.isEmpty(itemData)) {
            return itemDataList;
        }

        String[] rows = itemData.split("\r\n");
        for (int i = 1; i < rows.length; ++i) { // first line is name
            String row = rows[i];
            String[] data = row.split(",");

            ItemData itemData = new ItemData();
            itemData.itemcode = Integer.parseInt(data[0]);
            itemData.itemname = data[1];
            itemData.brand = data[2];
            itemData.sellercode = Integer.parseInt(data[3]);
            itemData.sellername = data[4];
            itemData.price = Integer.parseInt(data[5]);
            itemData.numberOfstock = Integer.parseInt(data[6]);
            itemData.numberOfsales = Integer.parseInt(data[7]);
            itemDataList.add(itemData);
        }

        return itemDataList;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("InsertDataBean{");
        sb.append("userData='").append(userData).append('\'');
        sb.append(", itemData='").append(itemData).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public class AccountData {

        private String email;
        private String password;
        private String firstname;
        private String lastname;
        private String birth;

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }

        public String getFirstname() {
            return firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public String getBirth() {
            return birth;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("AccountData{");
            sb.append("email='").append(email).append('\'');
            sb.append(", password='").append(password).append('\'');
            sb.append(", firstname='").append(firstname).append('\'');
            sb.append(", lastname='").append(lastname).append('\'');
            sb.append(", birth='").append(birth).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }

    public class ItemData {

        private int itemcode;
        private String itemname;
        private String brand;
        private int sellercode;
        private String sellername;
        private int price;
        private int numberOfstock;
        private int numberOfsales;

        public int getItemcode() {
            return itemcode;
        }

        public String getItemname() {
            return itemname;
        }

        public String getBrand() {
            return brand;
        }

        public int getSellercode() {
            return sellercode;
        }

        public String getSellername() {
            return sellername;
        }

        public int getPrice() {
            return price;
        }

        public int getNumberOfstock() {
            return numberOfstock;
        }

        public int getNumberOfsales() {
            return numberOfsales;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("ItemData{");
            sb.append("itemcode=").append(itemcode);
            sb.append(", itemname='").append(itemname).append('\'');
            sb.append(", brand='").append(brand).append('\'');
            sb.append(", sellercode=").append(sellercode);
            sb.append(", sellername='").append(sellername).append('\'');
            sb.append(", price=").append(price);
            sb.append(", numberOfstock=").append(numberOfstock);
            sb.append(", numberOfsales=").append(numberOfsales);
            sb.append('}');
            return sb.toString();
        }
    }
}
