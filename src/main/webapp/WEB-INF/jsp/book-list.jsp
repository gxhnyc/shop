<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"></c:set>

<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<!-- 调用布局，将内容插入布局的content区域 -->
<t:layout title="图书列表">
	<ul>
		<c:forEach items="${books}" var="book">
			<li>${book.book_id}-<a href="${contextPath }/books/${book.book_id}">《${book.book_name}》</a>			
			</li>
		</c:forEach>
	</ul>
</t:layout>

