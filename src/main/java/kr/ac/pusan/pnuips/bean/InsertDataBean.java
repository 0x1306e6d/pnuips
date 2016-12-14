package kr.ac.pusan.pnuips.bean;

import kr.ac.pusan.pnuips.csv.ItemData;
import kr.ac.pusan.pnuips.csv.UserData;

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

    public List<UserData> csvToAccountData() {
        return UserData.fromCsv(userData);
    }

    public List<ItemData> csvToItemData() {
        return ItemData.fromCsv(itemData);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("InsertDataBean{");
        sb.append("userData='").append(userData).append('\'');
        sb.append(", itemData='").append(itemData).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
