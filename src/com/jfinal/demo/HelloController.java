package com.jfinal.demo;

import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.aop.Enhancer;
import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;
import com.jfinal.inter.InterTest;
import com.jfinal.inter.InterTestInject;
@Before(InterTest.class)
public class HelloController extends Controller {
	public void index() {
		// 获取参数
		String str = getPara();
		System.out.println("str:"+str);
		// url：/hello/1232-hello  参数间以减号（-）为分隔符 
		System.out.println("第一个参数："+getPara(0)+"第二个参数："+getPara(1));
		// url：/hello?userName=liubaohua  问号挂参域值 参数为String类型
		System.out.println("参数名userName："+getPara("userName"));
		// url：/hello?age=23 自动转化为int
		System.out.println("自动将参数转化为int的方法："+getParaToInt("age"));
		
//		renderText("hello JFinal world !");
		renderJsp("/login.jsp");
//		renderError(500,"500error.html");
	}
	
//	不带参数时清除所有拦截器 ，带参时 清除 参数 指定的拦截器
	@Clear
	public void userName() {
		renderText("My name is liubaohua."+"参数是"+getPara());
		System.out.println("调用AOP业务层类");
		// 使用 enhance方法对业务层进行增强，使其具有AOP能力
		OrderService service = enhance(OrderService.class);
		service.test("liubaohua");
		
		// 使用Duang.duang方法在任何地方对目标进行增强
		// OrderService service = Duang.duang(OrderService.class);
		// 使用Enhancer.enhance方法在任何地方对目标进行增强
		// OrderService service = Enhancer.enhance(OrderService.class);
		
	}
	public void injectDemo() {
		// 为enhance方法传入的拦截器称为Inject拦截器，下面代码中的Intest1称为Inject拦截器
		OrderService service = Enhancer.enhance(OrderService.class,InterTestInject.class);
		service.testInject("为enhance方法传入的拦截器称为Inject拦截器");
		renderText("为enhance方法传入的拦截器称为Inject拦截器");
	}
	
//	@ActionKey("/login")
	public void login() {
		renderJsp("/login.jsp");
	}
	
}
