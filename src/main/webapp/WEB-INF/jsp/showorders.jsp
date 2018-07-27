<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath }"></c:set>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<title>所有订单</title>
<style type="text/css">
body {
	background: pink;
	text-align: center;
}

div {
	margin: 5px 350px;
}

ul {
	float: left;
	list-style-type: none;
}

a {
	text-decoration: none;
}

a:hover {
	color: red;
}

.inline {
	display: inline;
}
</style>


</head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<body>
	<div>
		<h2>所有订单</h2>
		<hr>
	</div>

	<div>
		<h2>
			<a href="${contextPath }/">返回首页</a>
		</h2>
	</div>
	<div>

		<table>
			<tr>
				<th>订单编号</th>
				<th>创建时间</th>
				<th>删除订单</th>
				<th>结算</th>
			</tr>
			<c:if test="${empty orders}">
				<tr>
					<td colspan="8">-------------------------------------空空如也！----------------------------------</td>
				</tr>
			</c:if>
			<c:if test="${not empty orders}">
				<c:forEach items="${orders.orders}" var="order">
					<tr>

						<td><a href="${contextPath }/uc/order-details/${order.o_id}">【${order.o_id}】</a></td>
						<td>【${order.createTime}】</td>
						<td>
							<form action="${contextPath }/uc/del-order/${order.o_id}"
								method="post">
								<sec:csrfInput />
								<input type="hidden" value="o_id"> <input type="submit"
									value="删除">
							</form>
						</td>
						<td>
							<form action="" method="post">
								<sec:csrfInput />
								<input type="hidden" value="o_id"> <input type="submit"
									value="结算">
							</form>
						</td>
					</tr>
				</c:forEach>
			</c:if>
		</table>

	</div>

	<div>
		<hr>
		@Copyright 2008-2028<br /> <strong>三头牛科技有限公司</strong>

	</div>
</body>
</html>