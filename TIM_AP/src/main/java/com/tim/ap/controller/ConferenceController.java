package com.tim.ap.controller;

import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tim.ap.entity.ConferenceEntity;
import com.tim.ap.service.ConferenceService;

@Controller
@RequestMapping("/conference")
public class ConferenceController {
	private static final Logger logger = LoggerFactory.getLogger(ConferenceController.class);

	@Autowired
	private ConferenceService conferenceService;

	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String loginForm() {
		return "/conference/join";
	}

	@RequestMapping(value = "/creat", method = RequestMethod.POST)
	public String createForm() {
		return "/conference/creat";
	}

	@RequestMapping(value = "/list", produces="text/plain;charset=UTF-8", method = RequestMethod.GET)
	public @ResponseBody String list(Locale locale) {
		logger.info("/conference/list", locale);

		List<ConferenceEntity> conferenceList = conferenceService.getConferenceList();

		return new com.google.gson.Gson().toJson(conferenceList);
	}

	@RequestMapping(value = "/update", produces="text/plain;charset=UTF-8", method = RequestMethod.GET)
	public @ResponseBody String update(Locale locale, @RequestParam("id") int id) {
		logger.info("/conference/update", locale);

		ConferenceEntity conferenceEntity = new ConferenceEntity();

		conferenceEntity.setId(id);
		conferenceEntity.setClosed("Y");

		conferenceService.updateConference(conferenceEntity);

		return new com.google.gson.Gson().toJson(conferenceEntity);
	}
}
