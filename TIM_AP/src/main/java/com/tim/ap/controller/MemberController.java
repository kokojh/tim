package com.tim.ap.controller;

import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tim.ap.entity.MemberEntity;
import com.tim.ap.service.MemberService;

@Controller
@RequestMapping("/member")
public class MemberController {
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	@Autowired
	private MemberService memberService;

	@RequestMapping("/list")
	public ModelAndView list(Locale locale) {
		logger.info("/member/list", locale);

		ModelAndView result = new ModelAndView();

		List<MemberEntity> memberList = memberService.getMemberList();

		result.addObject("result", memberList);
		result.setViewName("/member/list");

		return result;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginForm() {
		return "/member/login";
	}

	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String joinForm() {
		return "/member/join";
	}
}
