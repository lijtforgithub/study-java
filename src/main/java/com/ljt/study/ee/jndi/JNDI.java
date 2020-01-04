package com.ljt.study.ee.jndi;

import com.ljt.study.ee.jdbc.JDBC;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author LiJingTang
 * @date 2020-01-04 16:22
 */
public class JNDI {

    private static DataSource dataSource;
    private static Context context;

    private JNDI() {
    }

    static {
        try {
            context = new InitialContext();
            dataSource = (DataSource) context.lookup("java:/comp/env/jdbc/TEST");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public static DataSource newInstance() {
        return dataSource;
    }

    public static DataSource getDataSource(String contextName) {
        try {
            return (DataSource) context.lookup("java:/comp/env/" + contextName);
        } catch (NamingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Context getContext() {
        return context;
    }

    public static String findTestDataById(int id) {
        String data = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            ps = conn.prepareStatement("SELECT * FROM T_TEST WHERE id = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();

            while (rs != null && rs.next()) {
                data = rs.getString("data");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBC.close(conn, ps, rs);
        }

        return data;
    }

}
