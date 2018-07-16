<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"></c:set>

<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<!-- 调用布局，将内容插入布局的content区域 -->
<t:layout title="作者列表">
	<ul>
		<c:forEach items="${authors}" var="author">
			<li>#${author.author_id} : <a href="${contextPath }/authors/${author.author_id}">【${author.author_name}】</a><a>【${author.author_gender }】</a>
			<a href="${contextPath }/authors/${author.author_id}/author-edit">【编辑】</a><a href="${contextPath }/authors/${author.author_id}/author-delete">【删除】</a>
			</li>
		</c:forEach>
	</ul>

</t:layout>
