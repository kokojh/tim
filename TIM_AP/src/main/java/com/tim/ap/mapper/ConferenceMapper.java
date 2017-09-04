package com.tim.ap.mapper;

import java.util.ArrayList;

import com.tim.ap.entity.ConferenceEntity;

public interface ConferenceMapper {
	    ArrayList<ConferenceEntity> getConferenceList();
	    int insertConference(ConferenceEntity conferenceEntity);
	    void updateConference(ConferenceEntity conferenceEntity);
	    ConferenceEntity selectConference();
	    //void deleteConference(String id);
}
