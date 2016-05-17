package com.jfinal.demo;

import com.jfinal.config.Routes;
import com.jfinal.front.Index;

public class FrontRoutes extends Routes {

	@Override
	public void config() {
		// TODO Auto-generated method stub
		add("/",Index.class);
		add("/userlogin",Index.class);
	}

}
