import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
 
import com.kosign.wecafe.entities.Supplier;
public class AppSupplier {

	@SuppressWarnings("serial")
	public static void main(String[] args) {
		Configuration configuration = new Configuration();
		configuration.addAnnotatedClass(Supplier.class);
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
		
		Supplier supplier =new Supplier();
		supplier.setSupplierName("penhchet");
		supplier.setSupplierNumber("09877333");
		supplier.setSupplierAddress("Phnom Penh");
		supplier.setSupplierEmail("penhchet@gmail.com");
		
		session.save(supplier);
		session.getTransaction().commit();
		session.close();
	}

}
