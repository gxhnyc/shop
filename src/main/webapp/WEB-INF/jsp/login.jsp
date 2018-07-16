<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>管理员登录</title>
<style type="text/css">
	body{
		margin:0px 500px;
		background:pink;
	}
	fieldset{
	
		width:450px;
		height:500px;
	}
	legend{text-align:center;}
	form{
		text-align:left;
	}
	
	a{
		text-decoration:none;
	}
	a:hover{
		color:red;
	}
	#publisher_brief{
		width:400px;
		height:150px;
	}
	.add-error{
		background:yellow;
		color:red;
		font-weight:bold;
	}
	h2{color:red;}
</style>
</head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<body>
	<c:if test="${loginError}">
		<h2>用户名或密码错误！</h2>
	</c:if>
	<c:if test="${param.logout!=null }">
		<h2>用户已退出，请重新登录！</h2>
	</c:if>

	
	<fieldset>
		<legend>管理员登录</legend>
		<br><br><br>
		<!--action为空意味着会提交到当前表单页面的路径-->
		<form action="" method="post" >
		<!-- 防范csrf攻击 -->
		<sec:csrfInput/>		
		
		<div>
			<label for="username">用户名：</label>
			<input type="text" name="username" id="username"/>			
		</div>	
		<div>
			<label for="password">密　码：</label>
			<input type="password" name="password" id="password"/>			
		</div>
		<div>
			<button type="submit">登录</button>
		</div>
			
		</form>
	
	</fieldset>
</body>
</html>