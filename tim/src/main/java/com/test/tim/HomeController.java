package com.test.tim;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.test.VO.BasicVO;
import com.test.service.BasicServiceImpl;

@Controller
@RequestMapping("/tim")
public class HomeController {
	@Autowired
	private BasicServiceImpl basicService;
	
	@RequestMapping("/info")
	public String home(Locale locale, Model model) {
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		BasicVO info = basicService.selectInfo();
		model.addAttribute("serverTime", formattedDate );
		model.addAttribute("name",info.getName());
		model.addAttribute("age",info.getAge());
		
		return "home";
	}
	
}
