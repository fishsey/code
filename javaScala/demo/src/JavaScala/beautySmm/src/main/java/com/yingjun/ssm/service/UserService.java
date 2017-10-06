package com.yingjun.ssm.service;

import com.yingjun.ssm.entity.User;

import java.util.List;

public interface UserService {

	List<User> getUserList(int offset, int limit);
	 
}
