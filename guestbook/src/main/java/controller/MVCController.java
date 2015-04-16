package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import DAOImpl.ProductDAOImp;

@Controller
@RequestMapping("/*")
public class MVCController {
	@RequestMapping(value="test", method= RequestMethod.GET) 
	public String messageDelte(@RequestParam(required = true,
	value = "id") Integer id, Model model) { 
	ProductDAOImp pdao = new ProductDAOImp();
	
	model.addAttribute("name", pdao.findByID(id).getName()); 
	return "product"; 
	} 
	
}
