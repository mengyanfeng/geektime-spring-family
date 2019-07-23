package geektime.spring.family;

import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Arrays;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class PureSpringDatasourceDemoApplication {
	@Autowired
	private DataSource dataSource;

	public static void main(String[] args) throws  Exception{
		ApplicationContext applicationContext;
		applicationContext = new ClassPathXmlApplicationContext("application.xml");
		showBeans(applicationContext);
		dataSourceDemo(applicationContext);
	}

	@Bean(destroyMethod = "close")
	public DataSource dataSource() throws Exception {
		Properties properties = new Properties();
		properties.setProperty("driverClassName", "org.h2.Driver");
		properties.setProperty("url", "jdbc:h2:mem:testdb");
		properties.setProperty("username", "myfyy");
		return BasicDataSourceFactory.createDataSource(properties);
	}

	@Bean
	public PlatformTransactionManager transactonManager() throws Exception {
		return new DataSourceTransactionManager(dataSource());
	}

	private static void showBeans(ApplicationContext applicationContext) {
		System.out.println(Arrays.toString(applicationContext.getBeanDefinitionNames()));
	}

	private static void dataSourceDemo(ApplicationContext applicationContext) throws Exception{
		DataSource dataSource = applicationContext.getBean("dataSource", DataSource.class);
		System.out.println(dataSource.toString());
		Connection conn = dataSource.getConnection();
		System.out.println(conn.toString());
		conn.close();
	}
}
