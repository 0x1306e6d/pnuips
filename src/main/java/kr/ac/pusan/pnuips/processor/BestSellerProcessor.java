package kr.ac.pusan.pnuips.processor;

import com.google.common.collect.Lists;
import kr.ac.pusan.pnuips.DatabaseManager;
import kr.ac.pusan.pnuips.bean.SellBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BestSellerProcessor {

    private static final Logger logger = LoggerFactory.getLogger(BestSellerProcessor.class);

    public List<SellBean> searchExceptBestSeller(int sellercode) {
        logger.debug("Search except best seller request. sellercode={}", sellercode);
        List<SellBean> sellBeanList = Lists.newArrayList();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DatabaseManager.getConnection();
            ps = con.prepareStatement("SELECT * FROM ");
        } catch (SQLException e) {
            logger.error("Failed to search best seller request. sellercode=" + sellercode, e);
        }

        return sellBeanList;
    }
}
