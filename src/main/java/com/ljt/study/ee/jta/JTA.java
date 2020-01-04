package com.ljt.study.ee.jta;

import com.ljt.study.ee.jdbc.JDBC;
import com.ljt.study.ee.jndi.JNDI;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlXid;

import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.sql.XAConnection;
import javax.sql.XADataSource;
import javax.transaction.UserTransaction;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author LiJingTang
 * @date 2020-01-04 16:40
 */
public class JTA {

    private static DataSource dataSource1;
    private static DataSource dataSource2;
    private static XADataSource xaDataSource1;
    private static XADataSource xaDataSource2;
    private static UserTransaction userTx = null;

    private JTA() {
    }

    static {
        dataSource1 = JNDI.newInstance();
        dataSource2 = JNDI.getDataSource("jdbc/TEST_JTA");
        xaDataSource1 = getXADataSource("TEST");
        xaDataSource2 = getXADataSource("TEST_JTA");

        try {
            userTx = (UserTransaction) JNDI.getContext().lookup("java:comp/UserTransaction");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public static void testJTA(boolean flag) {
        Connection conn1 = null;
        PreparedStatement ps1 = null;
        Connection conn2 = null;
        PreparedStatement ps2 = null;
        String sql = "INSERT INTO T_USER(name, password) VALUES (?, ?)";

        try {
            conn1 = dataSource1.getConnection();
            conn2 = dataSource2.getConnection();

            userTx.begin();
            ps1 = conn1.prepareStatement(sql);
            ps2 = conn2.prepareStatement(sql);

            ps1.setString(1, "name_test");
            ps1.setString(2, "password_test");
            ps1.executeUpdate();

            ps2.setString(1, "name_jta");
            ps2.setString(2, "password_jta");
            ps2.executeUpdate();

            if (!flag)
                exception();
            userTx.commit();
        } catch (Exception e) {
            try {
                userTx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            JDBC.close(conn1, ps1);
            JDBC.close(conn2, ps2);
        }
    }

    /**
     * JTA 全局事务
     *
     * @version 2015年11月4日 下午7:01:40
     */
    public static void testJTA_XA(boolean flag) {
        XAResource xaResource1 = null;
        XAResource xaResource2 = null;
        XAConnection xaConn1 = null;
        XAConnection xaConn2 = null;
        Connection conn1 = null;
        Connection conn2 = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        String sql = "INSERT INTO T_USER(name, password) VALUES (?, ?)";

        int ret1 = 0;
        int ret2 = 0;
        Xid xid1 = new MysqlXid(new byte[]{1}, new byte[]{2}, 10);
        Xid xid2 = new MysqlXid(new byte[]{1}, new byte[]{3}, 10);

        try {
            xaConn1 = xaDataSource1.getXAConnection();
            xaConn2 = xaDataSource2.getXAConnection();
            xaResource1 = xaConn1.getXAResource();
            xaResource2 = xaConn2.getXAResource();
            conn1 = xaConn1.getConnection();
            conn2 = xaConn2.getConnection();
            ps1 = conn1.prepareStatement(sql);
            ps2 = conn2.prepareStatement(sql);
            ps1.setString(1, "name_test");
            ps1.setString(2, "password_test");
            ps2.setString(1, "name_jta");
            ps2.setString(2, "password_jta");

            xaResource1.start(xid1, XAResource.TMNOFLAGS);
            ps1.executeUpdate();
            xaResource1.end(xid1, XAResource.TMSUCCESS);

            if (xaResource2.isSameRM(xaResource1)) {
                xaResource2.start(xid1, XAResource.TMNOFLAGS);
                ps2.executeUpdate();
                xaResource2.end(xid1, XAResource.TMSUCCESS);
            } else {
                xaResource2.start(xid2, XAResource.TMNOFLAGS);
                ps2.executeUpdate();
                xaResource2.end(xid2, XAResource.TMSUCCESS);
                ret2 = xaResource2.prepare(xid2);
            }

            ret1 = xaResource1.prepare(xid1);

            if (!flag)
                exception();

            if (ret1 == XAResource.XA_OK && ret2 == XAResource.XA_OK) {
                xaResource1.commit(xid1, false);
                xaResource2.commit(xid2, false);
            }
        } catch (Exception e) {
            try {
                xaResource1.rollback(xid1);
                if (xaResource2.isSameRM(xaResource1)) {
                    xaResource2.rollback(xid1);
                } else {
                    xaResource2.rollback(xid2);
                }
            } catch (XAException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            JDBC.close(conn1, ps1);
            JDBC.close(conn2, ps2);
            JDBC.closeXAConnection(xaConn1);
            JDBC.closeXAConnection(xaConn2);
        }
    }

    private static XADataSource getXADataSource(String dbName) {
        MysqlXADataSource dataSource = new MysqlXADataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/" + dbName + "?useUnicode=true&amp;characterEncoding=UTF-8");
        dataSource.setUser("root");
        dataSource.setPassword("admin");

        return dataSource;
    }

    /**
     * 本地事务
     *
     * @version 2015年11月4日 下午6:48:51
     */
    public static void saveUser(boolean flag) {
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "INSERT INTO T_USER(name, password) VALUES (?, ?)";

        try {
            conn = dataSource1.getConnection();
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(sql);
            ps.setString(1, "name_main");
            ps.setString(2, "password_main");
            ps.executeUpdate();

            if (!flag)
                exception();
            conn.commit(); // 提交时保存数据 commit 之后数据无法再回滚 所以此句一定要在最后 异常之后
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            JDBC.close(conn, ps);
        }
    }

    private static void exception() {
        throw new RuntimeException("JTA_Test");
    }

}
