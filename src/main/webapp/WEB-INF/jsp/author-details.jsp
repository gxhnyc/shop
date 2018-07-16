<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"></c:set>

<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<!-- 调用布局，将内容插入布局的content区域 -->
<t:layout title="作者详情">

<h2>${author.author_name }</h2>
	<a>${author.author_brief }</a>
	<ul>
		<c:forEach items="${author.books}" var="book">			
			<li>#${book.book_id} </li>
			<li> 《${book.book_name}》</li>
			<li>【${book.publisher.publisher_name }	】</li>			
		</c:forEach>
	</ul>

</t:layout>
