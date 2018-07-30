<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"></c:set>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<t:layout title="购物车信息">
<jsp:attribute name="css">
    <sec:csrfMetaTags />
</jsp:attribute>

<jsp:attribute name="js">
  <script src="${contextPath}/assets/vendor/jquery-3.3.1.min.js"></script>
  <script src="${contextPath}/assets/js/shopping-cart.js"></script>
</jsp:attribute>
<jsp:body>
	<div>		
		<h2>
			<a href="${contextPath }/">返回首页</a>
		</h2>
	</div>
		
	<!-- 地址 -->
	<div>
		<h3>收货地址</h3>
		<a href="${contextPath }/uc/add-address">【新建收货地址】</a>
		<form action="" >
			<table>
				<tr>
					
					<th>收件人　　</th>
					<th>联系电话　　　　　</th>
					<th>详细地址　　　　　　　　　　　　　　　　　</th>
					<th>修改</th>
					<th>删除</th>
				</tr>
		
				<c:forEach items="${addresses }" var="addr">				
					<tr>						
						<td>${addr.consignee }</td>
						<td>${addr.tel }</td>
						<td>${addr.address }</td>
						<td>
							<form action="${contextPath }/uc/edit-address/${addr.ship_id } " method="get">
								<sec:csrfInput/>
								<input type="hidden" name="ship_id" value="${addr.ship_id }">
								<input type="submit" value="修改"/>						
							</form>
						</td>
						<td>
							<form action="${contextPath }/uc/del-address/${addr.ship_id }" method="get">
								<sec:csrfInput/>
								<input type="hidden" name="ship_id" value="${addr.ship_id }">
								<input type="submit" value="删除"/>						
							</form>
						</td>
						
					</tr>
				</c:forEach> 
				<tr>
					<td colspan="3">
						<a href="${contextPath }/uc/create-order">【创建订单】</a>
					</td>
				</tr>
			</table>
		</form>
		
	</div>
</jsp:body>
</t:layout>

