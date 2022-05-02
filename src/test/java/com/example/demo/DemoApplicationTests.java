package com.example.demo;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {
	@Autowired
	private UserService userService;

	private User user = new User(1,"surman","liushuman","123456",0,"1837311540");

	@Test
	void contextLoads() {
//		userService.register(user);

	}

}
