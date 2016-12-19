package kr.ac.pusan.pnuips.processor;

import kr.ac.pusan.pnuips.model.item.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class ItemProcessor {

    private static final Logger logger = LoggerFactory.getLogger(ItemProcessor.class);

    public Item searchItem(int itemcode) {
        try {
            Item item = new Item(itemcode);
            item.load();

            return item;
        } catch (SQLException e) {
            logger.error("Failed to search Item. itemcode=" + itemcode, e);
        }
        return null;
    }
}
