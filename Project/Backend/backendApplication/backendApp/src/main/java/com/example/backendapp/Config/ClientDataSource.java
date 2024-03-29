package com.example.backendapp.Config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ClientDataSource {
    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource ds;

    static {
        config.setJdbcUrl("jdbc:oracle:thin:@//localhost:1521/xe");
        config.setUsername("CLIENT");
        config.setPassword("client");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.setMaximumPoolSize(125);
        ds = new HikariDataSource(config);
    }

    private ClientDataSource() {
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }


}
