package com.tim.ap.service;

import java.util.ArrayList;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tim.ap.dao.AudioDao;
import com.tim.ap.entity.AudioEntity;
import com.tim.ap.mapper.AudioMapper;

@Repository
public class AudioService implements AudioDao {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public ArrayList<AudioEntity> getAudioList(AudioEntity audioEntity) {
		ArrayList<AudioEntity> result = new ArrayList<AudioEntity>();
		AudioMapper audioMapper = sqlSession.getMapper(AudioMapper.class);

		result = audioMapper.getAudioList(audioEntity);

		return result;
	}

	@Override
	public void insertAudio(AudioEntity audioEntity) {
		AudioMapper audioMapper = sqlSession.getMapper(AudioMapper.class);

		audioMapper.insertAudio(audioEntity);
	}

	@Override
	public void updateAudio(AudioEntity audioEntity) {
		AudioMapper audioMapper = sqlSession.getMapper(AudioMapper.class);

		audioMapper.updateAudio(audioEntity);	
	}

}
