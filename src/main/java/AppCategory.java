import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.kosign.wecafe.entities.Category;

public class AppCategory {
	
	public static void main(String[] args) {
		//Configuration
		Configuration configuration = new Configuration();
		configuration.addAnnotatedClass(Category.class);
		configuration.setProperties(new Properties(){
			{
				put("hibernate.connection.username","postgres");
				put("hibernate.connection.password","postgres");
				put("hibernate.connection.driver_class","org.postgresql.Driver");
				put("hibernate.connection.url","jdbc:postgresql://192.168.178.72:5432/WeCafe");
			}
			
		});
		
		SessionFactory sessionFactory = configuration
				.buildSessionFactory(new StandardServiceRegistryBuilder(
						).applySettings(configuration.getProperties()).build());
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Category category =new Category();
		category.setCatName("anything");
		category.setImg("");
		
		session.save(category);
		session.getTransaction().commit();
		session.close();
		
	}
}
