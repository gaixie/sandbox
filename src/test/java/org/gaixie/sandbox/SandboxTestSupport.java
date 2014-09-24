package org.gaixie.sandbox;

import com.google.inject.Guice;
import com.google.inject.Injector;

import org.gaixie.sandbox.config.SandboxConfig;
//import org.gaixie.sandbox.dao.DAOModule;
//import org.gaixie.sandbox.service.ServiceModule;
import org.gaixie.sandbox.utils.ConnectionUtils;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;


/**
 * 需要Guice注入的测试类的父类，使测试类取得injector，通过同步化保证injector只被创建一
 * 次，同时通过读取sandbox.properties文件的databaseType，使DAO的测试类可以无须修改就
 * 测试多种类型的数据库。
 */
public class SandboxTestSupport {
    private static Injector injector;

    public synchronized Injector getInjector() {
        if(injector != null){
            return injector;
        }
        String databaseType = SandboxConfig.getProperty("databaseType");
        injector = Guice.createInjector(/*new ServiceModule(),
                                          new DAOModule(databaseType)*/);
        return injector;
    }

    /**
     * 测试用例运行前需要清空所有表的数据。
     * 清表的顺序参考：sandbox-schema 文档
     */
    protected void clearTable() {
        Connection conn = null;
        try {
            conn = ConnectionUtils.getConnection();
            QueryRunner run = new QueryRunner();
            run.update(conn, "DELETE from schema_changes"); 
            DbUtils.commitAndClose(conn);
        } catch(SQLException e) {
            DbUtils.rollbackAndCloseQuietly(conn);
            System.out.println(e.getMessage());
        }
    }
}
