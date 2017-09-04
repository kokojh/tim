package com.tim.ap.mapper;

import java.util.ArrayList;

import com.tim.ap.entity.AudioEntity;

public interface AudioMapper {
		ArrayList<AudioEntity> getAudioList(AudioEntity audioEntity);
	    void insertAudio(AudioEntity audioEntity);
	    void updateAudio(AudioEntity audioEntity);
	    //void deleteAudio(AudioEntity audioEntity);
}
