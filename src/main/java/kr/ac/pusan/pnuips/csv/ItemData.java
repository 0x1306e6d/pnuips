package kr.ac.pusan.pnuips.csv;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import kr.ac.pusan.pnuips.model.cart.Cart;
import kr.ac.pusan.pnuips.model.item.Item;
import kr.ac.pusan.pnuips.model.order.Order;
import kr.ac.pusan.pnuips.model.sell.Sell;
import kr.ac.pusan.pnuips.model.sell.Seller;
import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class ItemData {

    public static List<ItemData> fromCsv(String csv) {
        List<ItemData> itemDataList = Lists.newArrayList();
        if (StringUtils.isEmpty(csv)) {
            return itemDataList;
        }

        String[] rows = csv.split("\r\n");
        for (int i = 1; i < rows.length; ++i) { // first line is name
            String row = rows[i];
            String[] data = row.split(",");

            ItemData itemData = new ItemData();
            Item item = new Item();

            item.setItemcode(Integer.parseInt(data[0]));
            item.setItemname(data[1]);
            item.setBrand(data[2]);
            itemData.setItem(item);

            Seller seller = new Seller();
            seller.setSellercode(Integer.parseInt(data[3]));
            seller.setSellername(data[4]);
            itemData.setSeller(seller);

            Sell sell = new Sell();
            sell.setItem(item);
            sell.setSeller(seller);
            sell.setPrice(Integer.parseInt(data[5]));
            sell.setNumberOfStock(Integer.parseInt(data[6]));
            sell.setNumberOfSales(Integer.parseInt(data[7]));
            itemData.setSell(sell);

            if (data.length > 8) {
                Set<Cart> cartSet = getCartSet(data[8]);
                for (Cart cart : cartSet) {
                    cart.setItemcode(item.getItemcode());
                    itemData.addCart(cart);
                }
            }

            if (data.length > 9) {
                Set<Order> orderSet = getOrderSet(data[9]);
                for (Order order : orderSet) {
                    order.setItemcode(item.getItemcode());
                    itemData.addOrder(order);
                }
            }

            itemDataList.add(itemData);
        }

        return itemDataList;
    }

    private static Set<Cart> getCartSet(String csv) {
        Set<Cart> cartSet = Sets.newHashSet();
        if (StringUtils.isEmpty(csv)) {
            return cartSet;
        }

        String[] rows = csv.split(Pattern.quote("|"));
        for (String row : rows) {
            String[] data = row.split(" ");

            Cart cart = new Cart();
            cart.setOwener(data[0]);
            cart.setCount(Integer.parseInt(data[1]));

            cartSet.add(cart);
        }
        return cartSet;
    }

    private static Set<Order> getOrderSet(String csv) {
        Set<Order> orderSet = Sets.newHashSet();
        if (StringUtils.isEmpty(csv)) {
            return orderSet;
        }

        String[] rows = csv.split(Pattern.quote("|"));
        for (String row : rows) {
            String[] data = row.split(" ");

            Order order = new Order();
            order.setPurchaser(data[0]);
            order.setCount(Integer.parseInt(data[1]));
            order.setDiscount(Integer.parseInt(data[2]));
            order.setTime(Timestamp.valueOf(data[3] + " " + data[4]));

            orderSet.add(order);
        }
        return orderSet;
    }

    private Item item;
    private Sell sell;
    private Seller seller;
    private final Set<Cart> cartSet;
    private final Set<Order> orderSet;

    public ItemData() {
        this.cartSet = Sets.newHashSet();
        this.orderSet = Sets.newHashSet();
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Sell getSell() {
        return sell;
    }

    public void setSell(Sell sell) {
        this.sell = sell;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public void addCart(Cart cart) {
        cartSet.add(cart);
    }

    public Set<Cart> getCartSet() {
        return cartSet;
    }

    public void addOrder(Order order) {
        orderSet.add(order);
    }

    public Set<Order> getOrderSet() {
        return orderSet;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ItemData{");
        sb.append("item=").append(item);
        sb.append(", sell=").append(sell);
        sb.append(", seller=").append(seller);
        sb.append('}');
        return sb.toString();
    }
}
