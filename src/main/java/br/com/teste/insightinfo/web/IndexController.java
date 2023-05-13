package br.com.teste.insightinfo.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("name")
public class IndexController {

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String showIndexPage(ModelMap model) {
		return "index";
	}
	
}
