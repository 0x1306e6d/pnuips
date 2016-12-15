package kr.ac.pusan.pnuips.model;

import java.sql.SQLException;

public interface Model {
    /**
     * 데이터베이스로 Model의 정보를 추가한다.
     *
     * @throws SQLException
     */
    void insert() throws SQLException;

    /**
     * 데이터베이스로부터 Model의 정보를 로드한다.
     *
     * @throws SQLException
     */
    void load() throws SQLException;

    /**
     * 데이터베이스의 Model의 정보를 수정한다.
     *
     * @throws SQLException
     */
    void update() throws SQLException;

    /**
     * 데이터베이스의 Model의 정보를 제거한다.
     *
     * @throws SQLException
     */
    void delete() throws SQLException;
}
