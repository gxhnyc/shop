<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath }"></c:set>
<c:set var="contextPath" value="${pageContext.request.contextPath }"></c:set>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<t:layout title="订单详情">
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
				<th>手机VPN编号</th>
				<th>品 牌</th>
				<th>型 号</th>
				<th>颜 色</th>
				<th>价格（元）</th>
				<th>数量(台)</th>
				<th>订单状态</th>

			</tr>
			<c:if test="${empty orderDetails}">
				<tr>
					<td colspan="8">-------------------------------------空空如也！----------------------------------</td>
				</tr>
			</c:if>
			<c:if test="${not empty orderDetails}">
				<c:forEach items="${orderDetails.orderItems}" var="item">
					<tr>
						<td>【${orderDetails.o_id}】</td>
						<td>【<fmt:formatDate value="${orderDetails.createTime}"
								pattern="YYYY-MM-DD HH:mm:ss"></fmt:formatDate>】
						</td>
						<td>【${item.cellphone.cp_id}】</td>
						<td>【${item.cellphone.cp_brand}】</td>
						<td>【${item.cellphone.cp_model}】</td>
						<td>【${item.cellphone.cp_color}】</td>
						<td>【${item.cellphone.cp_price/100}】</td>
						<td>【${ item.amount}】</td>
						<td>【${ orderDetails.stateText()}】</td>

					</tr>
				</c:forEach>
			</c:if>
			<tr>
				<td colspan="5"><h3>总计: ￥${orderDetails.totalResult()/100}
					</h3>
					<form action="${contextPath }/uc/cancel-order" method="post">
						<sec:csrfInput />
						<input type="hidden" name="o_id" value="${orderDetails.o_id}">
						<input type="submit" value="取消订单">
					</form>
					
					<form action="" method="post">
						<sec:csrfInput />
						<input type="hidden" value="o_id"> <input type="submit"
							value="结算订单">
					</form>
				</td>

				

			</tr>
		</table>
		<hr>


	</div>
	<div>

		<c:if test="${orderDetails.orderState == 'Created'}">
			<div>
				<form action="${contextPath}/uc/orders/${orderDetails.o_id}/pay"
					method="post">
					<sec:csrfInput />
					<button type="submit">支付宝付款</button>
				</form>
			</div>
		</c:if>
	</div>

</t:layout>