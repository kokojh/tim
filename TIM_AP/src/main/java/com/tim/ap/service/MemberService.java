package com.tim.ap.service;

import java.util.ArrayList;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tim.ap.dao.MemberDao;
import com.tim.ap.entity.MemberEntity;
import com.tim.ap.mapper.MemberMapper;

@Repository
public class MemberService implements MemberDao {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public ArrayList<MemberEntity> getMemberList() {
		ArrayList<MemberEntity> result = new ArrayList<MemberEntity>();
		MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);

		result = memberMapper.getMemberList();

		return result;
	}

}
