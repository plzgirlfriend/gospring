/*

MariaDB, Spring boot connect

 */

package main.gospring.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

// Spring context에 추가
@Configuration
public class ProjectConfig {

    // Datasource 정의(application.properties에)
    @Value("${gospring.datasource.url}")
    private String datasourceUrl;

    @Value("${gospring.datasource.username}")
    private String datasourceUserName;

    @Value("${gospring.datasource.password}")
    private String datasourcePassword;

    // Spring context에 이 method를 Bean으로 추가(return 값)
    @Bean
    // Datasource class, Spring에서는 관례로 HikariCP Datasource를 사용
    public DataSource dataSource(){

        // Spring의 관례인 HikariCP DS 사용
        HikariDataSource hikariDataSource = new HikariDataSource();

        // HikariCP DS에 url, name, password 정의
        hikariDataSource.setJdbcUrl(datasourceUrl);
        hikariDataSource.setUsername(datasourceUserName);
        hikariDataSource.setPassword(datasourcePassword);

        return hikariDataSource;
    }
}