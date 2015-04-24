package controller;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonMethod;
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

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


@Controller
@RequestMapping("/*")
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
	public ResponseEntity<String> hello(HttpServletRequest req){
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
		try {
            ObjectMapper objMapper = new ObjectMapper();
            String jsonString = objMapper.writeValueAsString("id：" + req.getRemoteUser());
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
	
	@Transactional
	@RequestMapping(value = "admin/addproduct", method = RequestMethod.POST)
	public void addProduct(HttpServletRequest req,
			@RequestParam("name") String name,
			@RequestParam("price") Integer price){
		Product product = new Product();
		product.setName(name);
		product.setPrice(price);
		productDAO.insert(product);
	}
	
	@RequestMapping(value = "vendo/addproduct", method = RequestMethod.POST)
	public void startSellProduct(HttpServletRequest req,
			@RequestParam("name") String name,
			@RequestParam("discount") String discount){
		Product product = productDAO.findByName(name);
		Supplier supplier = supplierDAO.findByName(req.getRemoteUser());
		SupplierProduct supplierProduct = new SupplierProduct();
		supplierProduct.setDiscount(discount);
		supplierProduct.setProductID(product.getId());
		supplierProduct.setSupplierID(supplier.getId());
		
	}
	
	@ResponseBody
	@Transactional
	@RequestMapping(value="admin/addvendor", method = RequestMethod.POST) 
	public ResponseEntity<String> addvendor(HttpServletRequest req,
			@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam("address") String address) throws Exception {
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
		 
		Users users = new Users();
		users.setENABLED(1);
		users.setPassword(password);
		users.setUserName(username);
		usersDAO.insert(users);
		
		Authorities authority = new Authorities();
		authority.setUsername(username);
		authority.setAuthority("ROLE_VENDO");
		authoritiesDAO.insert(authority);

		Supplier supplier = new Supplier();
		supplier.setAddress(address);
		supplier.setName(username);
		Supplier newSupplier = supplierDAO.insert(supplier);
		
		try {
            ObjectMapper objMapper = new ObjectMapper();
            String jsonString = objMapper.writeValueAsString(newSupplier);
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
	
	@Transactional
	@RequestMapping(value="user/updatepassword", method= RequestMethod.POST) 
	public void updatePassword(HttpServletRequest req,
			@RequestParam("password") String password) throws Exception {
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
		usersDAO.updatePassword(req.getRemoteUser(), password);
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
	
	@ResponseBody
	@RequestMapping(value="product/search", method= RequestMethod.GET) 
	public ResponseEntity<String> searchProducts(
			@RequestParam("namelike") String namelike) throws Exception {
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    List<SupplierProduct> lst = supplierProductDAO.searchByName(namelike);
		try {
			ObjectMapper objMapper = new ObjectMapper().setVisibility(JsonMethod.FIELD, Visibility.ANY);
            class Item {
            	String name;
            	int price;
            	String supplier;
            	String supplierAddress;
            	String discount;
            }
            List<Item> resLst = new ArrayList<Item>();
            for(SupplierProduct sp : lst) {
            	Product product = productDAO.findByID(sp.getProductID());
            	Supplier supplier = supplierDAO.findByID(sp.getSupplierID());
    			Item item = new Item();
    			item.name = product.getName();
    			item.price = product.getPrice();
    			item.supplier = supplier.getName();
    			item.discount = sp.getDiscount();
    			item.supplierAddress = supplier.getAddress();
    			resLst.add(item);
    		}
            String jsonString = objMapper.writeValueAsString(resLst);
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
	@RequestMapping(value="product/all", method= RequestMethod.GET) 
	public ResponseEntity<String> showAllProducts() throws Exception {
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
		List<SupplierProduct> lst = supplierProductDAO.findAll();
		try {
			ObjectMapper objMapper = new ObjectMapper().setVisibility(JsonMethod.FIELD, Visibility.ANY);
            class Item {
            	String name;
            	int price;
            	String supplier;
            	String supplierAddress;
            	String discount;
            }
            List<Item> resLst = new ArrayList<Item>();
            for(SupplierProduct sp : lst) {
            	Product product = productDAO.findByID(sp.getProductID());
            	Supplier supplier = supplierDAO.findByID(sp.getSupplierID());
    			Item item = new Item();
    			item.name = product.getName();
    			item.price = product.getPrice();
    			item.supplier = supplier.getName();
    			item.discount = sp.getDiscount();
    			item.supplierAddress = supplier.getAddress();
    			resLst.add(item);
    		}
            String jsonString = objMapper.writeValueAsString(resLst);
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
	@RequestMapping(value="user/addaccount", method= RequestMethod.POST) 
	public void addAccount(HttpServletRequest req,
			@RequestParam("accountnumber") String accountNumber,
			@RequestParam("bankname") String bankName) throws Exception {
		Account account = new Account();
		account.setAccountNumber(accountNumber);
		account.setBankName(bankName);
		String username = req.getRemoteUser();
		int customerID = customerDAO.findIDByUsername(username);
		account.setCustomerID(customerID);
		accountDAO.insert(account);
	}
	
	@ResponseBody
	@RequestMapping(value="user/addorder", method= RequestMethod.POST) 
	public void addOrder(HttpServletRequest req,
			@RequestParam("accountnumber") String accountNumber,
			@RequestParam("productID") int productID,
			@RequestParam("supplierID") int supplierID,
			@RequestParam("number") int number) throws Exception {
		Order order = new Order();
		order.setAccountNumber(accountNumber);
		order.setNumber(number);
		order.setProductID(productID);
		order.setSupplierID(supplierID);
		orderDAO.insert(order);
	}
	
	@ResponseBody
	@RequestMapping(value="user/order", method= RequestMethod.GET) 
	public ResponseEntity<String> getOrder(HttpServletRequest req) throws Exception {
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    
		int customerID = customerDAO.findIDByUsername(req.getRemoteUser());
		List<Account> accList = accountDAO.findAllByUserID(customerID);
		List<Order> orderList = new ArrayList<Order>();
		for(Account acc : accList) {
			List<Order> temp = orderDAO.findByAccountNumber(acc.getAccountNumber());
			orderList.addAll(temp);
		}
		
		try {
			ObjectMapper objMapper = new ObjectMapper().setVisibility(JsonMethod.FIELD, Visibility.ANY);
            class Item {
            	String productName;
            	double productPrice;
            	String supplierName;
            	String accountNumber;
            	String supplierAddress;
            	int number;
            }
            List<Item> resLst = new ArrayList<Item>();
            for(Order order : orderList) {
            	Product product = productDAO.findByID(order.getProductID());
            	Supplier supplier = supplierDAO.findByID(order.getSupplierID());
            	List<SupplierProduct> sps = supplierProductDAO.findByID(supplier.getId(), product.getId());
    			Item item = new Item();
    			item.productName = product.getName();
    			item.productPrice = order.getNumber() * product.getPrice() * Double.parseDouble(sps.get(0).getDiscount());
    			item.supplierName = supplier.getName();
    			item.accountNumber = order.getAccountNumber();
    			item.number = order.getNumber();
    			item.supplierAddress = supplier.getAddress();
    			resLst.add(item);
    		}
            String jsonString = objMapper.writeValueAsString(resLst);
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
	@RequestMapping(value="user/account", method= RequestMethod.GET) 
	public ResponseEntity<String> getAccount(HttpServletRequest req) throws Exception {
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
		String username = req.getRemoteUser();
		int customerID = customerDAO.findIDByUsername(username);
		List<Account> lst = accountDAO.findAllByUserID(customerID);
		try {
            ObjectMapper objMapper = new ObjectMapper();
            String jsonString = "";
            for(Account acc : lst) {
    			jsonString += objMapper.writeValueAsString(acc);
    		}
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
