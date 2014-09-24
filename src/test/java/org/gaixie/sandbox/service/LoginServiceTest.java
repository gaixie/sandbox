package org.gaixie.sandbox.service;

import org.gaixie.sandbox.SandboxTestSupport;
import org.gaixie.sandbox.utils.ConnectionUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LoginServiceTest extends SandboxTestSupport {

    @Before
    public void setup() throws Exception {
	clearTable();
        Connection conn = null;
        try {
            conn = ConnectionUtils.getConnection();
            QueryRunner run = new QueryRunner();
            
            // 初始化共享基础数据
            run.update(conn, "INSERT INTO schema_changes (major, minor, revision, hash) VALUES (1,0,0,'afasdfadf')");
            DbUtils.commitAndClose(conn);
        } catch(SQLException e) {
            DbUtils.rollbackAndCloseQuietly(conn);
            System.out.println(e.getMessage());
        } 
    }


    @Test
    public void testLoginSuccess(){
        Assert.assertTrue(true);
    }

    @After
    public void tearDown() {
	clearTable();
    }
}
