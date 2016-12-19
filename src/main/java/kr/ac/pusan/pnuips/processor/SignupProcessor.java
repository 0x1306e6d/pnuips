package kr.ac.pusan.pnuips.processor;

import kr.ac.pusan.pnuips.bean.SignupBean;
import kr.ac.pusan.pnuips.model.account.Account;
import kr.ac.pusan.pnuips.model.account.Grade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.sql.SQLException;

public class SignupProcessor {

    private static final Logger logger = LoggerFactory.getLogger(SignupProcessor.class);

    public enum SignupResult {
        SUCCESS,
        ALREADY_EXISTS_ACCOUNT,
        SYSTEM_ERROR
    }

    public SignupResult signup(SignupBean bean) {
        try {
            Account account = new Account(bean.getEmail());

            if (account.isExist()) {
                return SignupResult.ALREADY_EXISTS_ACCOUNT;
            }

            account.setPassword(bean.getPassword());
            account.setFirstname(bean.getFirstname());
            account.setLastname(bean.getLastname());
            account.setBirthday(Date.valueOf(bean.getBirthday()));
            account.setGrade(Grade.NORMAL);
            account.setTotalPrice(0);
            account.insert();

            return SignupResult.SUCCESS;
        } catch (SQLException e) {
            logger.error("Failed to signup. bean=" + bean, e);
        }

        return SignupResult.SYSTEM_ERROR;
    }
}
