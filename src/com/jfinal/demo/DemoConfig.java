package com.jfinal.demo;


import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PropKit;
import com.jfinal.model.User;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.PostgreSqlDialect;
import com.jfinal.plugin.activerecord.tx.TxByActionKeyRegex;
import com.jfinal.plugin.activerecord.tx.TxByActionKeys;
import com.jfinal.plugin.activerecord.tx.TxByMethodRegex;
import com.jfinal.plugin.activerecord.tx.TxByMethods;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.render.ViewType;

public class DemoConfig extends JFinalConfig {

	@Override
	public void configConstant(Constants me) {
		// TODO Auto-generated method stub
		me.setDevMode(true);
		//第一次使用use加载的配置将成为主配置，可以通过PropKit.get(...)直接取值
		PropKit.use("config.properties");
//		me.setDevMode(PropKit.getBoolean("devMode"));
		
		// 设置编码集
		me.setEncoding("utf-8");
		// 设置分隔符
		me.setUrlParaSeparator("&");
		// 设置页面格式
		me.setViewType(ViewType.JSP);
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
		// 声明式事务
//		// actionKey 正则  实现方式---拦截器
//		me.add(new TxByActionKeyRegex("/user.*"));
//		// actionKeys
//		me.add(new TxByActionKeys("/user/save","/user/testDb"));
//		// actionMethods
//		me.add(new TxByMethods("save","update"));
//		// action methodRegex
//		me.add(new TxByMethodRegex("(.*save.*|.*update.*)"));
		
	}

	@Override
	public void configPlugin(Plugins me) {
		// TODO Auto-generated method stub
//		 数据库操作
		C3p0Plugin c3p0Plugin = new C3p0Plugin(PropKit.get("jdbcUrl"),PropKit.get("username"),PropKit.get("password"),PropKit.get("driver"));
		ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
		
		arp.setShowSql(true);
		
		arp.addMapping("post_user","user_id",User.class);
		// 复合主键
//		arp.addMapping("post_user", "user_id,user_class_id",User.class);
		
		me.add(c3p0Plugin);
		me.add(arp);
		
		// 添加缓存插件
		me.add(new EhCachePlugin());
		// 设置方言
		arp.setDialect(new PostgreSqlDialect());
		
	}
	/*public static void main(String[] args) {
		JFinal.start("WebRoot",8081,"/",5);
	}*/

}
