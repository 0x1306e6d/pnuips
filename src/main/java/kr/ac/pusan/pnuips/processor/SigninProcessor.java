package kr.ac.pusan.pnuips.processor;

import kr.ac.pusan.pnuips.bean.SigninBean;
import kr.ac.pusan.pnuips.model.account.Account;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class SigninProcessor {

    private static final Logger logger = LoggerFactory.getLogger(SigninProcessor.class);

    public enum SigninResult {
        SUCCESS,
        INVALID_PASSWORD,
        NOT_EXISTS_ACCOUNT,
        SYSTEM_ERROR
    }

    /**
     * 로그인
     *
     * @param signinBean 로그인 BEAN
     * @return 로그인 결과
     */
    public SigninResult signin(SigninBean signinBean) {
        logger.debug("Signin request. signinBean={}", signinBean);

        try {
            Account account = new Account(signinBean.getEmail());
            if (account.isExist()) {
                account.load();

                if (StringUtils.equals(account.getPassword(), signinBean.getPassword())) {
                    return SigninResult.SUCCESS;
                } else {
                    return SigninResult.INVALID_PASSWORD;
                }
            } else {
                return SigninResult.NOT_EXISTS_ACCOUNT;
            }
        } catch (SQLException e) {
            logger.error("Failed to signin. signinBean=" + signinBean, e);
        }

        return SigninResult.SYSTEM_ERROR;
    }
}
