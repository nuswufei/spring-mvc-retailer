package controller;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import DAO.*;
import entity.*;

import javax.servlet.http.HttpServletRequest;

import java.util.logging.Level;
import java.util.logging.Logger;


@Controller
@RequestMapping("/")
public class MVCController {
	private static final Logger log = Logger.getLogger( MVCController.class.getName());
	@Autowired
	AccountDAO accountDAO;
	
	@Autowired
	AuthoritiesDAO authoritiesDAO;
	
	@Autowired
	private CustomerDAO customerDAO;
	
	@Autowired
	private OrderDAO orderDAO;
	
	@Autowired
	private ProductDAO productDAO;
	
	@Autowired
	private SupplierDAO supplierDAO;
	
	@Autowired
	private SupplierProductDAO supplierProductDAO;
	
	@Autowired
	private UsersDAO usersDAO;
	
	@ResponseBody
	@RequestMapping(value="test", method= RequestMethod.GET)
	public ResponseEntity<String> registration(){
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
		try {
            ObjectMapper objMapper = new ObjectMapper();
            String jsonString = objMapper.writeValueAsString("testsuccess");
            return new ResponseEntity<String>(jsonString, headers, HttpStatus.OK);
        }
        catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		return null;
	}
	
	@ResponseBody
	@Transactional
	@RequestMapping(value="registration", method= RequestMethod.POST) 
	public ResponseEntity<String> registration(HttpServletRequest req,
			@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam("name") String name,
			@RequestParam("address") String address) throws Exception {
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
		 
		Users users = new Users();
		users.setENABLED(1);
		users.setPassword(password);
		users.setUserName(username);
		usersDAO.insert(users);
		log.log(Level.WARNING, "userInsert");
		
		Authorities authority = new Authorities();
		authority.setUsername(username);
		authority.setAuthority("ROLE_USER");
		authoritiesDAO.insert(authority);

		Customer customer = new Customer();
		customer.setName(name);
		customer.setAddress(address);
		customer.setUsername(username);
		Customer newCustomer = customerDAO.insert(customer);
		
		try {
            ObjectMapper objMapper = new ObjectMapper();
            String jsonString = objMapper.writeValueAsString(newCustomer);
            return new ResponseEntity<String>(jsonString, headers, HttpStatus.OK);
        }
        catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		
		return null;
	} 
	
	@RequestMapping(value="user/logout", method= RequestMethod.GET) 
	@Transactional
	public void logOut (HttpServletRequest req) { 
		req.getSession(true).invalidate();
	} 
	public void test(HttpServletRequest req) {
		 
		String user = req.getRemoteUser() + "test";
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON); 
	   new ResponseEntity<String>(user, headers, HttpStatus.OK);
	}
}
