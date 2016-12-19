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

    /**
     * 회원가입
     *
     * @param signupBean 회원가입 BEAN
     * @return 회원가입 결과
     */
    public SignupResult signup(SignupBean signupBean) {
        logger.debug("Signup request. signupBean={}", signupBean);

        try {
            Account account = new Account(signupBean.getEmail());

            if (account.isExist()) {
                return SignupResult.ALREADY_EXISTS_ACCOUNT;
            }

            account.setPassword(signupBean.getPassword());
            account.setFirstname(signupBean.getFirstname());
            account.setLastname(signupBean.getLastname());
            account.setBirthday(Date.valueOf(signupBean.getBirthday()));
            account.setGrade(Grade.NORMAL);
            account.setTotalPrice(0);
            account.insert();

            return SignupResult.SUCCESS;
        } catch (SQLException e) {
            logger.error("Failed to signup. signupBean=" + signupBean, e);
        }

        return SignupResult.SYSTEM_ERROR;
    }
}
