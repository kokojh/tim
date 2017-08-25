package com.test.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.test.VO.BasicVO;

@Repository
public class BasicDaoImpl implements BasicDao{
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public BasicVO selectinfo() {
		System.out.println("여긴와요?3");
		BasicVO info = new BasicVO();
		info = (BasicVO) sqlSession.selectOne("selectinfo");
		return info;
	}
}
