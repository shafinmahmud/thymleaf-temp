package shafin.web.config;

import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;


//@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("web.shafin.repository")
@PropertySource("classpath:mysql.properties")
public class DatabaseConfiguration {
    
  private static final String PROPERTY_NAME_DATABASE_DRIVER = "jdbc.driver";
  private static final String PROPERTY_NAME_DATABASE_PASSWORD = "jdbc.pass";
  private static final String PROPERTY_NAME_DATABASE_URL = "jdbc.url";
  private static final String PROPERTY_NAME_DATABASE_USERNAME = "jdbc.user";
       
  private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
  private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
  private static final String ENTITYMANAGER_PACKAGES_TO_SCAN = "com.infancyit.bdmobile.entity";
   
      @Resource
      private Environment env;
       
      @Bean
      public ComboPooledDataSource dataSource() throws IllegalStateException, PropertyVetoException {
    	      ComboPooledDataSource dataSource = new ComboPooledDataSource();
               
              dataSource.setDriverClass(env.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
              dataSource.setJdbcUrl(env.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));
              dataSource.setUser(env.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME));
              dataSource.setPassword(env.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD));
               
              return dataSource;
      }
       
      @Bean
      public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws IllegalStateException, PropertyVetoException {
              LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
              entityManagerFactoryBean.setDataSource(dataSource());
              entityManagerFactoryBean.setPackagesToScan(ENTITYMANAGER_PACKAGES_TO_SCAN);
              
              HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        	  jpaVendorAdapter.setShowSql(Boolean.valueOf(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
              entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
                                           
              entityManagerFactoryBean.setJpaProperties(hibProperties());               
              return entityManagerFactoryBean;
      }
       
      
      private Properties hibProperties() {
              Properties properties = new Properties();
              properties.put(PROPERTY_NAME_HIBERNATE_DIALECT, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
              properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
              return properties;        
      }
       
      @Bean
      public JpaTransactionManager transactionManager() throws IllegalStateException, PropertyVetoException {
              JpaTransactionManager transactionManager = new JpaTransactionManager();
              transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
              return transactionManager;
      }


}
