package com.example.backendapp.Config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class JpaConfig {

    //how to make specific datasource primary in runtime?


    @Bean(name = "DBAdminDataSource")
    @Primary
    public DataSource DbAdminDataSource() {
        final DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url("jdbc:oracle:thin:@localhost:1521:xe?useUnicode=true&characterEncoding=UTF-8&useSSL=false&allowPublicKeyRetrieval=true");
        dataSourceBuilder.username("kursach_admin");
        dataSourceBuilder.password("qwerty");
        return dataSourceBuilder.build();
    }
}
