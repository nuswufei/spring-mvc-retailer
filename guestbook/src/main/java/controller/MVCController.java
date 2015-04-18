package controller;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import DAOImpl.UsersDAOImpl;
import entity.Users;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/*")
public class MVCController {
	private UsersDAOImpl usersDAO;
	@Autowired
	@ResponseBody
	@RequestMapping(value="registration", method= RequestMethod.POST) 
	@Transactional
	public ResponseEntity<String> registration(HttpServletRequest req,
			@PathVariable("username") String username,
			@PathVariable("password") String password,
			@PathVariable("name") String name,
			@PathVariable("address") String address) {
		this.usersDAO = usersDAO;
		Users users = new Users();
		users.setENABLED(1);
		users.setPassword(password);
		users.setUserName(username);
		usersDAO.insert(users);
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
		try {
            ObjectMapper objMapper = new ObjectMapper();
            String jsonString = objMapper.writeValueAsString(users);
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
