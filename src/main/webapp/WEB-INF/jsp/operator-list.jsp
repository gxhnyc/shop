<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Book-List</title>
<link href="${contextPath}/assets/css/add-error.css" rel="stylesheet">
</head>
<body>
	<h2>Operator-List</h2>
	<ul>
		<c:forEach items="${operators}" var="operator">
			<li>${operator.id}-<a href="${contextPath }/operators/operator-details/${operator.id}">《${operator.username}》</a>	
				<a href="${contextPath }/operators/operator-delete/${operator.id}">【删除】</a>		
			</li>
		</c:forEach>
	</ul>
</body>



</html>