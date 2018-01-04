import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.kosign.wecafe.entities.Product;
import com.kosign.wecafe.entities.User;
import com.kosign.wecafe.entities.UserRole;
import com.kosign.wecafe.enums.Status;
import com.kosign.wecafe.enums.UserRoleType;
import com.kosign.wecafe.services.UserService;
import com.kosign.wecafe.services.UserServiceImpl;
import com.kosign.wecafe.util.HibernateUtil;

public class ApplicationSecurity {
	
	
	public static void main(String[] args) {
		
		//UserService userService = new UserServiceImpl();
	
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		User user = new User();
		
		Session session = null;
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			
			
			user.setEmail("customer@gmail.com");
			user.setFirstName("customer");
			user.setLastName("customer");
			user.setStatus(Status.ACTIVE);
			user.setUsername("customer");
			user.setPassword(passwordEncoder.encode("customer"));

			UserRole userRole = new UserRole();
			userRole = (UserRole) session.get(UserRole.class, 3L);
			//userRole.setId(1L);
			//userRole.setType(UserRoleType.CUSTOMER);
			
			user.getUserRoles().add(userRole);

			//session.save(userRole);
			session.save(user);
			
			//userService.saveUser(user);
			
			session.getTransaction().commit();
		}catch(Exception ex){
			ex.printStackTrace();
			session.getTransaction().rollback();
		}finally{
			session.close();
		}
		
		
	}

}
