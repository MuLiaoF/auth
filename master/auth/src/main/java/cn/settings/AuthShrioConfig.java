package cn.settings;


import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;


@Configuration
@MapperScan(basePackages = {"cn.wandingkeji.auth.mapper"})
@ComponentScan(basePackages = {"cn.wandingkeji.auth.service","cn.wandingkeji.auth.controller","cn.wandingkeji.settings"})
public class AuthShrioConfig {


    @Autowired
    ApplicationContext applicationContext;


    @Bean
    public SqlSessionFactoryBean lazySqlSessionFactory() throws IOException {

        DataSource dataSource = applicationContext.getBean(DataSource.class);
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();

        PathMatchingResourcePatternResolver classPathResource = new PathMatchingResourcePatternResolver();
        Resource[] resources = classPathResource.getResources("cn/feng/mapping/*.xml");

        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(resources);
        return sqlSessionFactoryBean;
    }



}
