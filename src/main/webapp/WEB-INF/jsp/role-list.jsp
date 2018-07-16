<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Role-List</title>
<link href="${contextPath}/assets/css/add-error.css" rel="stylesheet">
</head>
<body>
	<h2>Role-List</h2>
	<ul>
		<c:forEach items="${roles}" var="role">
			<li><p>${role.role_id}-<a href="${contextPath }/roles/role-details/${role.role_id}">《${role.role_name}》</a></p>	
				<a href="${contextPath }/roles/role-delete/${role.role_id}">【删除】</a>
				<a href="${contextPath }/roles/role-edit/${role.role_id}">【编辑】</a>		
			</li>
		</c:forEach>
	</ul>
</body>



</html>