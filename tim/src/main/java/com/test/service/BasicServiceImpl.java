package com.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.VO.BasicVO;
import com.test.dao.BasicDaoImpl;

@Service
public class BasicServiceImpl implements BasicService {
	@Autowired
	private BasicDaoImpl basicDao;
	
	@Override
	public BasicVO selectInfo() {
		System.out.println("여긴와요?2");
		BasicVO info = new BasicVO();
		info = basicDao.selectinfo();
		return info;
	}

}
