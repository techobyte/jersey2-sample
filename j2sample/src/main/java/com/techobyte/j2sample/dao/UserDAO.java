package com.techobyte.j2sample.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.techobyte.j2sample.mapper.UserMapper;
import com.techobyte.j2sample.model.User;


public class UserDAO {
	protected SqlSessionFactory sqlSessionFactory;
	
	public UserDAO() {
		sqlSessionFactory = DBConnectionFactory.getSqlSessionFactory();
	}

	public List<User> selectAll() {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			//List<User> list = session.selectList("User.getAll");
			com.techobyte.j2sample.mapper.UserMapper userMapper = session.getMapper(UserMapper.class);
			return userMapper.getAllUsers();
		} finally {
			session.close();
		}
	}
	
	public User selectById(Integer id){
		SqlSession session = sqlSessionFactory.openSession();
		
		try {
			//User usr = (User) session.selectOne("User.getById", id);
			UserMapper userMapper = session.getMapper(UserMapper.class);
			return userMapper.getUserById(id);
		} finally {
			session.close();
		}
	}
}
