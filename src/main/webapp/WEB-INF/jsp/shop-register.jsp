<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>operator-add</title>
<style type="text/css">
	body{
		background:pink;
	}
	a{
		text-decoration:none;
	}
	a:hover{
		color:red;
	}
	.add-error{
		color:red;
	}
</style>
</head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<body>
	<h2>用户注册</h2>
	<fieldset>
		<legend>用户注册</legend>
		<!--action为空意味着会提交到当前表单页面的路径-->
		<form:form action="" method="post" commandName="customer">
		<div>
			<label for="username">用户名：</label>
			<input type="text" name="username" id="username"/>
			<form:errors path="username" cssClass="add-error"></form:errors>
		</div>
		<div>
			<label for="password">密　码：</label>
			<input type="password" name="password" id="password"/>
			<form:errors path="password" cssClass="add-error"></form:errors>
		</div>
		
		<div>
			<button type="submit">提交</button>
		</div>
		</form:form>
	
	</fieldset>
</body>
</html>