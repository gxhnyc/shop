<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath }"></c:set>

<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<!-- 调用布局，将内容插入布局的content区域 -->
<t:layout title="图书详情">

<h2>《${book.book_name}》</h2>
	<ul>
		<c:forEach items="${book.author}" var="author">
			<li>作者：<a>${author.author_name } </a>	</li>		
		</c:forEach>
	</ul>
	<ul>
		
			<li>出版社：<a>【${book.publisher.publisher_name } 】</a>	</li>		
		
	</ul>

</t:layout>