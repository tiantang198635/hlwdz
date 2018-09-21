package com.sign.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sign.entity.User;

@Mapper
public interface UserDao {
	public User insert(User record);
	public List<User> getUser();
	public User findUserByName(String name);
}
