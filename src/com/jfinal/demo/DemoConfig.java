package com.jfinal.demo;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;

public class DemoConfig extends JFinalConfig {

	@Override
	public void configConstant(Constants me) {
		// TODO Auto-generated method stub
		me.setDevMode(true);
		//第一次使用use加载的配置将成为主配置，可以通过PropKit.get(...)直接取值
//		PropKit.use("a_little_config.txt");
//		me.setDevMode(PropKit.getBoolean("devMode"));
		
		// 设置编码集
		me.setEncoding("utf-8");
		// 设置分隔符
		me.setUrlParaSeparator("&");
	}
	
	@Override
	public void configRoute(Routes me) {
		// TODO Auto-generated method stub
		me.add("/login", HelloController.class);
		me.add("/user",UserController.class);
		me.add(new FrontRoutes());
	}

	@Override
	public void configHandler(Handlers me) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configInterceptor(Interceptors me) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configPlugin(Plugins me) {
		// TODO Auto-generated method stub
		// 数据库操作
//		loadPropertyFile("your_app_config.txt");
//		C3p0Plugin c3p0Plugin = new C3p0Plugin(getProperty("jdbcUrl"),
//		getProperty("user"), getProperty("password"));
//		me.add(c3p0Plugin);
//		ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
//		me.add(arp);
//		arp.addMapping("user", User.class);
	}
	public static void main(String[] args) {
		JFinal.start("WebRoot",80,"/",5);
	}

}
