package com.jfinal.demo;

import com.jfinal.core.Controller;
import com.jfinal.model.User;

public class UserController extends Controller {
	
	public void save() {
		User user = getModel(User.class);
		// 如果表单域的名称为 "myUser.title"可加上一个参数来获取
		user = getModel(User.class , "myUser");
	}
	
	public void login() {
		System.out.println("用户名:"+getPara("userName")+"\t密码："+getPara("userPass"));
		renderText("登录成功！");
	}
}
