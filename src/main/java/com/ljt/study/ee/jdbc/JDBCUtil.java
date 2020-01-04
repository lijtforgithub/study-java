package com.ljt.study.ee.jdbc;

import javax.sql.XAConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author LiJingTang
 * @date 2020-01-04 15:15
 */
public class JDBCUtil {

    public static void closeConnection(Connection conn) {
        if (null != conn) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeXAConnection(XAConnection conn) {
        if (null != conn) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closePreparedStatement(PreparedStatement ps) {
        if (null != ps) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeResultSet(ResultSet rs) {
        if (null != rs) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(Connection conn, PreparedStatement ps) {
        closeConnection(conn);
        closePreparedStatement(ps);
    }

    public static void close(Connection conn, PreparedStatement ps, ResultSet rs) {
        closeConnection(conn);
        closePreparedStatement(ps);
        closeResultSet(rs);
    }

}
