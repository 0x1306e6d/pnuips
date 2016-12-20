package kr.ac.pusan.pnuips.processor;

import kr.ac.pusan.pnuips.model.item.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class ItemProcessor {

    private static final Logger logger = LoggerFactory.getLogger(ItemProcessor.class);

    /**
     * 상품을 검색한다
     *
     * @param itemcode 상품의 itemcode
     * @return 상품 객체
     */
    public Item searchItem(int itemcode) {
        logger.debug("Search item request. itemcode={}", itemcode);
        try {
            Item item = new Item(itemcode);
            if (item.isExist()) {
                item.load();

                return item;
            }
        } catch (SQLException e) {
            logger.error("Failed to search Item. itemcode=" + itemcode, e);
        }
        return null;
    }
}
