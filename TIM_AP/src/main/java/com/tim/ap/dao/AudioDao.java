package com.tim.ap.dao;

import java.util.ArrayList;

import com.tim.ap.entity.AudioEntity;

public interface AudioDao {
	public ArrayList<AudioEntity> getAudioList(AudioEntity audioEntity);
    public void insertAudio(AudioEntity audioEntity);
    public void updateAudio(AudioEntity audioEntity);
    //public void deleteAudio(AudioEntity audioEntity);
}
