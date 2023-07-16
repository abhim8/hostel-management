package com.project.hostelmanagement.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Slf4j
@Configuration
public class DBConfig {

    @Value("${spring.datasource.driverClassName:com.mysql.jdbc.Driver}")
    private String driverClassName;

    @Value("${spring.datasource.url:jdbc:mysql://localhost:3307/hostel_management}")
    private String url;

    @Value("${spring.datasource.username:root}")
    private String userName;

    @Value("${spring.datasource.password:password}")
    private String password;

    @Primary
    @Bean
    public DataSource dataSource() {
        log.info("Datasource configuration");
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        return dataSource;
    }

}
