package com.jfinal.inter;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

public class InterTestInject implements Interceptor{
	public void intercept(Invocation inv) {
		// TODO Auto-generated method stub
		System.out.println("拦截器testInject执行之前");
		inv.invoke();
		System.out.println("拦截器testInject执行之后");
	}
}
