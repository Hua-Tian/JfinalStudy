package com.jfinal.inter;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

public class InterTest implements Interceptor {

	public void intercept(Invocation inv) {
		// TODO Auto-generated method stub
		System.out.println("拦截器执行之前");
		inv.invoke();
		System.out.println("拦截器执行之后");
	}

}
