package kr.ac.pusan.pnuips.bean;

import com.google.common.collect.Lists;
import kr.ac.pusan.pnuips.model.sell.Sell;
import kr.ac.pusan.pnuips.model.sell.Seller;

import java.util.List;

public class SellerBean {

    private Seller seller;
    private final List<Sell> sellList = Lists.newArrayList();

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public List<Sell> getSellList() {
        return sellList;
    }
}
