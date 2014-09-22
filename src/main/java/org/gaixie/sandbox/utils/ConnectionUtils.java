package org.gaixie.sandbox.utils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.gaixie.sandbox.config.SandboxConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据库链接工具类。饿汉模式加载 DataSource。
 */
public class ConnectionUtils {
    private static final Logger logger = LoggerFactory.getLogger(ConnectionUtils.class);
    private static DataSource dataSource = null;

    static {
        try {
            dataSource = BasicDataSourceFactory.createDataSource(SandboxConfig.getProperties());
            logger.info("Successfully created datasource.");
        } catch (Exception e) {
            logger.error("Create DataSource failed.", e);
        }
    }

    /**
     * 得到连接池最大 Active 连接数。
     * @return 最大Active连接数
     */
    public static int getMaxActive() {
        return ((BasicDataSource) dataSource).getMaxActive();
    }

    /**
     * 得到连接池当前 Active 连接数。
     * @return Active连接数
     */
    public static int getNumActive() {
        return ((BasicDataSource) dataSource).getNumActive();
    }

    /**
     * 得到连接池当前 Idle 连接数。
     * @return Idle连接数
     */
    public static int getNumIdle() {
        return ((BasicDataSource) dataSource).getNumIdle();
    }

    /**
     * 从连接池拿一个数据库连接。
     * @return 数据库连接
     * @exception SQLException 获取连接失败时抛出。
     */
    public static Connection getConnection() throws SQLException {
        return  dataSource.getConnection();
    }

    /**
     * 设置连接池的自动提交状态，默认读取 sandbox.properties 配置文件。
     * @param defaultAutoCommit 是否自动提交。
     */
    public static void setDefaultAutoCommit(boolean defaultAutoCommit) {
        BasicDataSource bds = (BasicDataSource) dataSource;
        bds.setDefaultAutoCommit(defaultAutoCommit);
    }
}
