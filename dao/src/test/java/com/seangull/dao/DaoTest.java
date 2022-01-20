package com.seangull.dao;

import com.seangull.dao.redis.RedisInteractor;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
public class DaoTest {

    @Autowired
    DataSource dataSource;

    @Autowired
    RedisInteractor redisInteractor;

    @Test
    public void dataSourceTest() throws SQLException {
        System.out.println("datasource: " + dataSource.getClass().getName());
        HikariDataSource hikariDataSource = (HikariDataSource) dataSource;
        System.out.println("hikariDataSource MinimumIdle: " + hikariDataSource.getMinimumIdle());
        System.out.println("hikariDataSource MaximumPoolSize: " + hikariDataSource.getMaximumPoolSize());
        System.out.println("hikariDataSource IdleTimeout: " + hikariDataSource.getIdleTimeout());
        System.out.println("hikariDataSource MaxLifetime: " + hikariDataSource.getMaxLifetime());
        System.out.println("hikariDataSource ConnectionTimeout: " + hikariDataSource.getConnectionTimeout());
        System.out.println("hikariDataSource ConnectionTestQuery: " + hikariDataSource.getConnectionTestQuery());
        /* 测试连接 */
        Connection connection = dataSource.getConnection();
        System.out.println("connection: " + connection);
        connection.close();
    }

    @Test
    public void redisTest() throws SQLException {
        System.out.println(redisInteractor);
        /* 测试设置test的值，成功后，可使用redis图形化工具查看test的值 */
        System.out.println(redisInteractor.set("test","test"));
    }

}
