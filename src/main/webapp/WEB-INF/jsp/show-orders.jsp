<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath }"></c:set>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:layout title="所有订单">
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
						<td>【<fmt:formatDate value="${order.createTime}"  pattern="yyyy-MM-dd hh:mm:ss"></fmt:formatDate>】</td>
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
</t:layout>

