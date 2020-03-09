package com.wangyi.example.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = DataSourceConfig.PACKAGE, sqlSessionFactoryRef = "sqlSessionFactory")
public class DataSourceConfig {

    // Dao 扫描路径
    static final String PACKAGE = "com.wangyi.example.dao";
    // Mapper 扫描路径
    static final String MAPPER_LOCATION = "classpath:mapper/*.xml";

    @Value("${jdbc.driverClassName:com.mysql.cj.jdbc.Driver}")
    private String driverClassName;
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.username}")
    private String username;
    @Value("${jdbc.password}")
    private String password;
    @Value("${datasource.initialSize:5}")
    private int initialSize;
    @Value("${datasource.minIdle:5}")
    private int minIdle;
    @Value("${datasource.maxActive:20}")
    private int maxActive;
    @Value("${datasource.maxActive:60000}")
    private long maxWait;
    @Value("${datasource.validationQuery:SELECT 1 FROM DUAL}")
    private String validationQuery;
    @Value("${datasource.testWhileIdle:true}")
    private boolean testWhileIdle;

    @Bean(name = "dataSource")
    @Primary
    public DruidDataSource initDruidDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setInitialSize(initialSize);
        dataSource.setMinIdle(minIdle);
        dataSource.setMaxActive(maxActive);
        dataSource.setMaxWait(maxWait);
        dataSource.setValidationQuery(validationQuery);
        dataSource.setTestWhileIdle(testWhileIdle);
        return dataSource;
    }

    @Bean(name = "sqlSessionFactory")
    @Primary
    public SqlSessionFactory initSqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATION));
        sqlSessionFactory.setPlugins(new PaginationInterceptor()); // 分页插件
        return sqlSessionFactory.getObject();
    }

    @Bean(name = "transactionManager")
    @Primary
    public DataSourceTransactionManager initDataSourceTransactionManager(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
