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
	
		<sec:authorize access="hasAnyAuthority('Limi_BOOK_RW','Limi_BOOK_RO')">
		<ul class="menu">
			<li><a href="">-图书管理-</a></li>			
			<li><a href="${contextPath }/books/">图书列表</a></li>			
			<sec:authorize access="hasAuthority('Limi_BOOK_RW')">
			<li><a href="${contextPath }/books/book-add">添加图书</a></li>
			</sec:authorize>
		</ul>
		</sec:authorize>
		
		<sec:authorize access="hasAnyAuthority('Limi_AUTHOR_RW','Limi_AUTHOR_RO')">
		<ul class="menu">
			<li><a href="">-作者管理-</a></li>
			<sec:authorize access="hasAnyAuthority('Limi_AUTHOR_RW','Limi_AUTHOR_RO')">
			<li><a href="${contextPath }/authors/">作者列表</a></li>
			</sec:authorize>
			<sec:authorize access="hasAuthority('Limi_AUTHOR_RW')">
			<li><a href="${contextPath }/authors/author-add">添加作者</a></li>
			</sec:authorize>
		</ul>
		</sec:authorize>
		
		<sec:authorize access="hasAnyAuthority('Limi_PUBLISHER_RW','Limi_PUBLISHER_RO')">
		<ul class="menu">
			<li><a href="">-出版社管理-</a></li>
			<li><a href="${contextPath }/publishers/">出版社列表</a></li>
			<sec:authorize access="hasAuthority('Limi_PUBLISHER_RW')">
			<li><a href="${contextPath }/publishers/publisher-add">添加出版社</a></li>
			</sec:authorize>
		</ul>
		</sec:authorize>
		
		<ul class="menu">
		<sec:authentication property="principal.username" var="o_username" scope="session"/>
		<sec:authentication property="principal.operator.email" var="o_email"/>
			<li><a href="">--当前用户--</a></li>
			<!--  principal属性可以拿到当前登录的用户详情（UserDetailsImpl） -->
			<li>用户名：<a href="">【${o_username }】</a></li>			
			
			<c:choose>	
				<c:when test="${o_email!=null }">
					<li>邮　箱：<a href="">【${o_email }】</a></li>
				</c:when>
				<c:otherwise>
					<li>邮　箱：<a href="">【未登记邮箱】</a></li>
				</c:otherwise>
			</c:choose>	
			<li>
			 <!-- springsecurity默认的退出路径是：POST /logout，注意：springsecurity自带处理 -->
				<form action="${contextPath }/logout" method="post">
					<sec:csrfInput/>
					<button type="submit">退出</button>
				</form>
			</li>
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