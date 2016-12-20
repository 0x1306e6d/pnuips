package kr.ac.pusan.pnuips.tool;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import kr.ac.pusan.pnuips.csv.ItemData;
import kr.ac.pusan.pnuips.csv.UserData;
import kr.ac.pusan.pnuips.model.account.Account;
import kr.ac.pusan.pnuips.model.cart.Cart;
import kr.ac.pusan.pnuips.model.item.Item;
import kr.ac.pusan.pnuips.model.order.Order;
import kr.ac.pusan.pnuips.model.sell.Sell;
import kr.ac.pusan.pnuips.model.sell.Seller;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SampleDataCreator {

    private static final Logger logger = LoggerFactory.getLogger(SampleDataCreator.class);

    private static final String USER_CSV_HEADER = "email,password,firstname,lastname,birth,coupons\r\n";
    private static final String ITEM_CSV_HEADER = "itemcode,itemname,brand,sellercode,sellername,price,numberOfstock,numberOfsales,cart,purchase\r\n";
    private static final int[] RANDOM_DISCOUNT = new int[]{0, 10, 30, 40};
    private static final List<UserData> userDataList = Lists.newArrayList();
    private static final List<ItemData> itemDataList = Lists.newArrayList();
    private static final List<Seller> sellerList = Lists.newArrayList();
    private static final ExecutorService executorService = Executors.newCachedThreadPool();
    private static final Scanner scanner = new Scanner(System.in);

    private static int userDataCount = 0;
    private static int itemDataCount = 0;

    public static void main(String[] args) throws IOException {
        System.out.println("Sample Data Creator Start");

        System.out.println("User data count : ");
        userDataCount = scanner.nextInt();

        System.out.println("Item data count : ");
        itemDataCount = scanner.nextInt();

        for (int i = 0; i < userDataCount; i++) {
            UserData userData = new UserData();
            userData.setAccount(createRandomAccount());

            userDataList.add(userData);
            if (userDataList.size() % 100 == 0) {
                logger.debug("[UserData] current size={}", userDataList.size());
            }
        }

        for (int i = 0; i < itemDataCount; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    ItemData itemData = new ItemData();
                    itemData.setItem(createRandomItem());
                    itemData.setSeller(createRandomSeller());
                    itemData.setSell(createRandomSell(itemData.getItem().getItemcode(), itemData.getSeller().getSellercode()));
                    itemData.setCartSet(createRandomCart(itemData.getItem().getItemcode(), itemData.getSeller().getSellercode()));
                    itemData.setOrderSet(createRandomOrder(itemData.getItem().getItemcode(), itemData.getSeller().getSellercode(), itemData.getSell().getNumberOfStock()));
                    itemData.getSell().setNumberOfSales(itemData.getOrderSet().size());

                    itemDataList.add(itemData);
                    if (itemDataList.size() % 100 == 0) {
                        logger.debug("[ItemData] current size={}", itemDataList.size());
                    }
                }
            });
        }

        executorService.shutdown();
        logger.info("Create user data. size={}", userDataList.size());
        logger.info("Create item data. size={}", itemDataList.size());
        logger.info("Total seller size={}", sellerList.size());
        try {
            Path usercsv = Paths.get("output", "user.csv");
            Files.deleteIfExists(usercsv);
            if (!Files.exists(usercsv)) {
                Files.createFile(usercsv);
                logger.info("Create user.csv");
            }
            Files.write(usercsv, USER_CSV_HEADER.getBytes());

            for (UserData userData : userDataList) {
                StringBuilder sb = new StringBuilder();
                sb.append(userData.getAccount().getEmail()).append(',');
                sb.append(userData.getAccount().getPassword()).append(',');
                sb.append(userData.getAccount().getFirstname()).append(',');
                sb.append(userData.getAccount().getLastname()).append(',');
                sb.append(userData.getAccount().getBirthday()).append(',');
                sb.append("\r\n");

                Files.write(usercsv, sb.toString().getBytes(), StandardOpenOption.APPEND);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("Finish write user.csv");

        try {
            Path itemcsv = Paths.get("output", "item.csv");
            Files.deleteIfExists(itemcsv);
            if (!Files.exists(itemcsv)) {
                Files.createFile(itemcsv);
                logger.info("Create item.csv");
            }
            Files.write(itemcsv, ITEM_CSV_HEADER.getBytes());

            for (ItemData itemData : itemDataList) {
                if (itemData == null) {
                    continue;
                }
                StringBuilder sb = new StringBuilder();
                sb.append(itemData.getItem().getItemcode()).append(',');
                sb.append(itemData.getItem().getItemname()).append(',');
                sb.append(itemData.getItem().getBrand()).append(',');
                sb.append(itemData.getSeller().getSellercode()).append(',');
                sb.append(itemData.getSeller().getSellername()).append(',');
                sb.append(itemData.getSell().getPrice()).append(',');
                sb.append(itemData.getSell().getNumberOfStock()).append(',');
                sb.append(itemData.getSell().getNumberOfSales()).append(',');

                for (Cart cart : itemData.getCartSet()) {
                    sb.append(cart.getOwener()).append(' ');
                    sb.append(cart.getCount()).append('|');
                }
                sb.append(',');
                for (Order order : itemData.getOrderSet()) {
                    sb.append(order.getPurchaser()).append(' ');
                    sb.append(order.getCount()).append(' ');
                    sb.append(order.getDiscount()).append(' ');
                    sb.append(order.getTime()).append('|');
                }
                sb.append("\r\n");

                Files.write(itemcsv, sb.toString().getBytes(), StandardOpenOption.APPEND);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("Finish write item.csv");
    }

    private static Account createRandomAccount() {
        Account account = new Account();
        StringBuilder sb = new StringBuilder();
        sb.append(RandomStringUtils.randomAlphabetic(1, 7))
                .append('@')
                .append(RandomStringUtils.randomAlphabetic(4))
                .append(".com");
        account.setEmail(sb.toString());
        account.setPassword(RandomStringUtils.randomNumeric(4));
        account.setFirstname(RandomStringUtils.randomAlphabetic(8));
        account.setLastname(RandomStringUtils.randomAlphabetic(4));
        account.setBirthday(new Date(
                RandomUtils.nextInt(90, 115),
                RandomUtils.nextInt(0, 11),
                RandomUtils.nextInt(1, 30)));
        return account;
    }

    private static Item createRandomItem() {
        Item item = new Item();
        item.setItemcode(RandomUtils.nextInt());
        item.setItemname(RandomStringUtils.randomAlphabetic(8));
        item.setBrand(RandomStringUtils.randomAlphabetic(5));
        return item;
    }

    private static Seller createRandomSeller() {
        if (RandomUtils.nextBoolean() && RandomUtils.nextBoolean() && sellerList.size() > 0) {
            return sellerList.get(RandomUtils.nextInt(0, sellerList.size()));
        }
        Seller seller = new Seller();
        seller.setSellercode(RandomUtils.nextInt());
        seller.setSellername(RandomStringUtils.randomAlphabetic(8));
        sellerList.add(seller);
        return seller;
    }

    private static Sell createRandomSell(int itemcode, int sellercode) {
        Sell sell = new Sell(itemcode, sellercode);
        sell.setPrice(RandomUtils.nextInt());
        sell.setNumberOfStock(RandomUtils.nextInt());
        return sell;
    }

    private static Set<Cart> createRandomCart(int itemcode, int sellercode) {
        Set<Cart> cartSet = Sets.newHashSet();
        int size = RandomUtils.nextInt(0, 100);
        for (int i = 0; i < size; i++) {
            Cart cart = new Cart();
            cart.setItemcode(itemcode);
            cart.setSellercode(sellercode);
            cart.setOwener(userDataList.get(RandomUtils.nextInt(0, userDataList.size())).getAccount().getEmail());
            cart.setCount(RandomUtils.nextInt());

            cartSet.add(cart);
        }
        return cartSet;
    }

    private static Set<Order> createRandomOrder(int itemcode, int sellercode, int stock) {
        Set<Order> orderSet = Sets.newHashSet();
        int size = RandomUtils.nextInt(0, 100);
        for (int i = 0; i < size; i++) {
            Order order = new Order();
            order.setItemcode(itemcode);
            order.setSellercode(sellercode);
            order.setPurchaser(userDataList.get(RandomUtils.nextInt(0, userDataList.size())).getAccount().getEmail());
            order.setCount(RandomUtils.nextInt(1, stock));
            order.setDiscount(RANDOM_DISCOUNT[RandomUtils.nextInt(0, RANDOM_DISCOUNT.length)]);
            order.setTime(new Timestamp(2016,
                    RandomUtils.nextInt(0, 11),
                    RandomUtils.nextInt(1, 31),
                    RandomUtils.nextInt(0, 23),
                    RandomUtils.nextInt(0, 59),
                    RandomUtils.nextInt(0, 59),
                    0));

            orderSet.add(order);
        }
        return orderSet;
    }
}
