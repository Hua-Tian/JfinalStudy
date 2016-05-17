package com.jfinal.demo;

import com.jfinal.aop.Before;
import com.jfinal.inter.InterTest;

// 定义需要使用AOP的业务层类
public class OrderService {
	// 配置事务拦截器
	@Before(InterTest.class)
	public void test(String name) {
		System.out.println(name+"使用AOP的业务层类");
	}
	
	public void testInject(String name) {
		System.out.println(name+"使用AOP的业务层类");
	}
}
