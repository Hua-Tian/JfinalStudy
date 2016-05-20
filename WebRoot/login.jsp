<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>login</title>
</head>
<body>
	<form action="/user/login" method="post">
		ユーザー：<input name="userName" type="text" value="${userName}"><font color="red">${nameMsg}</font>
		</br>
		密码：<input name="userPass" type="password"><font color="red">${passMsg}</font>
		</br>
		邮箱：<input type="text" name="email" value="${email}"><font color="red">${emailMsg}</font>
		</br>
		电话：<input type="text" name="phone" value="${phone}"><font color="red">${phoneMsg}</font>
		</br><input type="submit" value="提交">
	</form>
	<br/>
	学生姓名：${userName}<br/>所属班级：${userClass}
</body>
</html>