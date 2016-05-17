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
		ユーザー：<input name="user.userName" type="text">
		</br>
		密码：<input name="user.userPass" type="password">
		<input type="submit" value="提交">
	</form>
</body>
</html>