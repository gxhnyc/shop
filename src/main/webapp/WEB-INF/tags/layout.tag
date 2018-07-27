<%@ tag language="java" pageEncoding="UTF-8"%>
<!-- 调用该tag时还需传参title以指定页面标题 -->
<%@ attribute name="title" required="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath }"></c:set>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		
		<title>${title}</title>
		<link href="${contextPath}/assets/css/add-error.css" rel="stylesheet">
		<style>
			.menu {
				display: inline-block;
			}
		</style>
	</head>
	<body>
	<div class="header">
		
		<ul class="menu">
		<sec:authentication property="principal.username" var="c_username" scope="session"/>
			<li><a href="">--当前用户--</a></li>
			<!--  principal属性可以拿到当前登录的用户详情（UserDetailsImpl） -->
			<li>用户名：<a href="">【${c_username }】</a></li>			
			
			<c:choose>	
				<c:when test="${o_email!=null }">
					<li>邮　箱：<a href="">【${o_email }】</a></li>
				</c:when>
				<c:otherwise>
					<li>邮　箱：<a href="">【未登记邮箱】</a></li>
				</c:otherwise>
			</c:choose>	
			
			<c:choose>	
				<c:when test="${c_username!=null||!c_username.equals('')}">
					<li><!-- springsecurity默认的退出路径是：POST /logout，注意：springsecurity自带处理 -->
						<form action="${contextPath }/logout" method="post">
							<sec:csrfInput/>
							<a href="${contextPath }/uc/showcart">【查看购物车】</a>
							<a href="${contextPath }/uc/showorders">【我的订单】</a>
							<button type="submit">退出</button>
						</form>
						
					</li>
				</c:when>
				<c:otherwise>
					<li><!-- springsecurity默认的退出路径是：POST /logout，注意：springsecurity自带处理 -->
						<form action="${contextPath }/login" method="post">
							<sec:csrfInput/>
							<button type="submit">登录</button>
						</form>
					</li>
					<li>
						<form action="${contextPath }/register" method="post">
							<sec:csrfInput/>
							<button type="submit">注册</button>
						</form>
					</li>
				</c:otherwise>
			</c:choose>	
			 
			
		</ul>
	</div>
	<hr>
	<div class="content">
		<!-- 取tag参数值 -->
		<h2>${title }</h2>
		<!-- 把layout标签的内容插入到此处 -->
		<jsp:doBody></jsp:doBody>
	</div>
	<hr>
	<div class="footer">
		copyright@ 2018
	</div>
	
	
	</body>
</html>