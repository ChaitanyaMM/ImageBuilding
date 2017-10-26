package com.imageapi.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imageapi.Dao.UserDao;
import com.imageapi.Objects.User;
import com.imageapi.Service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userdao;

	public String sample() {
		System.out.println("IN  service IMpl is  CALLING ");

		return userdao.sample();
	}

	public User save(User user) {
		return userdao.save(user);
	}

}
