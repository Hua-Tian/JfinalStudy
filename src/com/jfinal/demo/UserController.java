package com.jfinal.demo;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.core.JFinal;
import com.jfinal.model.User;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheInterceptor;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.ehcache.CacheName;
import com.jfinal.plugin.ehcache.EvictInterceptor;
import com.jfinal.plugin.ehcache.IDataLoader;
import com.jfinal.validator.LoginValidator;

public class UserController extends Controller {
	
    //		User user = getModel(User.class);
	// 如果表单域的名称为 "myUser.title"可加上一个参数来获取
    //		user = getModel(User.class , "myUser");
	
	public void save() {
		// 用户删除
		User.user.deleteById(4);
		
		// 用户添加
		new User().set("user_name", "宝华"+new Random().nextInt(100))
				  .set("user_password", "123")
		          .set("user_class_id", 1)
		          .save();
		
		renderText("用户添加成功...");
		// 用户查询
		User user = User.user.findById(2);
		renderText(user.getStr("user_name"));
		// 用户修改
		User.user.findById(1).set("user_name","张无忌").update();
		// 查询语句
		String likeStr = "select * from post_user where user_name like '%宝%'";
		List<User> userList = User.user.find(likeStr);
		String renderStr = "";
		for (User user2 : userList) {
			renderStr += user2.getStr("user_name");
			renderStr += "---";
		}
		renderText(renderStr);
		// 分页查询语句 参数列表（起始页，每页条数，查询字段，表和条件《使用占位符》，"设置占位符"）
		Page<User> userPage = User.user.paginate(1, 8,
				"select *", "from post_user where user_name like ?", "%华%");
		
		renderStr = "";
		for (User user2 : userPage.getList()) {
			renderStr += "-->";
			renderStr += user2.getStr("user_name")+"\n";
		}
		renderText("总页数："+userPage.getTotalPage()+"\t共有："+userPage.getTotalRow()+"条数据"
				+"\t共有："+userPage.getTotalPage()+"页"+"\t当前页："+userPage.getPageNumber()
				+"\n"+renderStr);
		
	}
	// Db + User 模式
	public void testDb() {
		// 添加用户
		Record user = new Record().set("user_name", "刘宝华"+new Random().nextInt(200))
							  .set("user_password", "12233")
							  .set("user_class_id", 2);
		Db.save("post_user","user_id", user);
		System.out.println("用户添加成功");
		// 删除用户
		boolean isOk = Db.deleteById("post_user","user_id", user.getInt("user_id"));
		if(isOk){
			System.out.println("用户删除成功");
		}
		// 查询用户
		user = Db.findById("post_user","user_id",20);
		// 修改用户
		user = Db.findById("post_user","user_id", 19).set("user_name", "华天");
		Db.update("post_user","user_id",user);
		// 获取user 的user_name属性
		String userName = user.getStr("user_name");
		// 分页操作
		Page<Record> userPage = Db.paginate(1, 10, 
				"select *", "from post_user where user_class_id = ?",2);
	
		System.out.println("总页数："+userPage.getTotalPage()+"\t共有："+userPage.getTotalRow()+"条数据"
				+"\t共有："+userPage.getTotalPage()+"页"+"\t当前页："+userPage.getPageNumber());
		
		// 事务处理
		boolean successed = Db.tx(new IAtom() {
			
			public boolean run() throws SQLException {
				// TODO Auto-generated method stub
				// 添加用户
				Record user = new Record().set("user_name", "刘宝华"+new Random().nextInt(200))
									  .set("user_password", "12233")
									  .set("user_class_id", 2);
				boolean saveOk = Db.save("post_user","user_id", user);
				if(saveOk){
					System.out.println("事务————用户添加成功");
				}
				// 删除用户
				boolean isOk = Db.deleteById("post_user","user_id", user.getInt("user_id"));
				if(isOk){
					System.out.println("事务————用户删除成功");
				}
				return saveOk && isOk;
			}
		}); 
		
		String suc = "";
		if(successed){
			suc = "事务操作成功";
		}
		
		renderText(userName+"-"+suc);
		
		// 复合主键    select * from "post_user" where "user_id" = ? and "user_class_id" = ?
		user = Db.findById("post_user", "user_id,user_class_id",33,1);
		System.out.println("测试复合主键："+user.getStr("user_password"));
	}
	// 多表关联操作
	public void relation() {
		// 方式一
		String sql = "select * from post_user u inner join post_class c on u.user_class_id = c.class_id"
				   + " where u.user_name= ?";
		User user = User.user.findFirst(sql,"liubaohua");
		System.out.println(user.getStr("class_name"));
		
		renderText("多表关联-操作成功！"+"\n学生："+user.getStr("user_name")+"所在班级是："+user.getStr("class_name"));
		// 方式二
		
	}
	// 测试缓存  需要在ehcache.xml 中进行配置 例如：<cache name="/user/testCache"...>
	@Before(CacheInterceptor.class)
	// 使用注解的方式，取代默认的actionKey作为actionName  需要在ehcache.xml 中进行配置 例如：<cache name="testCache"...>
	@CacheName("testCache") 
	public void testCache() {
		System.out.println("测试Cache");
		String sql = "select * from post_user u inner join post_class c on u.user_class_id = c.class_id"
				   + " where u.user_name= ?";
		User user = User.user.findFirst(sql,"liubaohua");
		
		System.out.println("多表关联-操作成功！"+"\n学生："
						+user.getStr("user_name")+"所在班级是："+user.getStr("class_name"));
		// 传参
		setAttr("userName", user.getStr("user_name"));
		setAttr("userClass", user.getStr("class_name"));
		
		renderJsp("/login.jsp");
	}
	
	/**
	 * EvictInterceptor可以根据CacheName注解自动清除缓存
	 */
	@Before(EvictInterceptor.class)
	@CacheName("testCache")
	public void testEvict() {
		redirect("/login.html");
	}
	
	/**
	 * CacheKit 是缓存操作工具类
	 */
	public void testCacheKit() {
		final String sql = "select * from post_user u inner join post_class c on u.user_class_id = c.class_id"
				   + " where u.user_name= ?";
		// 从缓存中取出
		User user = CacheKit.get("testCache", "user");
		if(user == null) {
			user = User.user.findFirst(sql,"张三丰");
			// 放入到缓存
			CacheKit.put("testCache", "user",user);
		}
		setAttr("userName", user.getStr("user_name"));
		setAttr("userClass", user.getStr("class_name"));
		
		
		/**
		 * CacheKit重载CacheKit.get(String,String,IDataLoader)方法示例
		 *
		 * CacheKit.get方法提供一个IDataLoader接口，该接口中的load方法在缓存不存在时才会被调用
		 * 
		 */
		user = CacheKit.get("testCache", "user",new IDataLoader() {
			
			public Object load() {
				// TODO Auto-generated method stub
				return User.user.findFirst(sql,"张三丰");
			}
		});
		
		render("/login.html");
	}
	
	
	@Before(LoginValidator.class)
	public void login() {
		System.out.println("用户名:"+getPara("userName")+"\t密码："+getPara("userPass"));
		String sql = "select * from post_user where user_name = '"+getPara("userName")
												+"' and user_password = '"+getPara("userPass")+"'";
		User user = User.user.findFirst(sql);
		
		if(user != null) {
			System.out.println("test");
			renderText("登录成功！");
		}else {
			renderText("登录失败！");
		}
	}
	
	
	public static void main(String[] args) {
		JFinal.start("WebRoot",8081,"/",5);
	}
}
