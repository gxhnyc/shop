<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"></c:set>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<t:layout title="创建订单...">
	<div>		
		<h2>
			<a href="${contextPath }/">返回首页</a>
		</h2>
	</div>
	<div>		
		<table>
			<tr>
				<th>手机VPN编号</th>
				<th>品　牌</th>
				<th>型　号</th>
				<th>颜　色</th>
				<th>价格（元）</th>
				<th>小计（元）</th>
				<th>数量</th>
				<th>操作</th>
			</tr>
			<c:if test="${empty shoppingCart.cartitems}">
			<tr>
				<td colspan="8">-------------------------------------空空如也！----------------------------------</td>
			</tr>
			</c:if>
			<c:if test="${not empty shoppingCart.cartitems}">
			<c:forEach items="${ shoppingCart.cartitems}" var="item">
			<tr>				
				<td>【${item.cellphone.cp_id}】</td>
				<td>【${item.cellphone.cp_brand}】</td>
				<td>【${item.cellphone.cp_model}】</td>
				<td>【${item.cellphone.cp_color}】</td>
				<td>【${item.cellphone.cp_price/100}】</td>
				<td>【${item.cellphone.cp_price/100 *item.amount}】</td>
				<td>
					
		          	${item.amount}
		          	          
        		</td>       	
				<td>
					<form action="${contextPath }/uc/remove-item" method="post">
						<sec:csrfInput/>
						<input type="hidden" name="cp_id" value="${item.cellphone.cp_id}">
						<input type="submit" value="删除">
					</form>
				</td>
			</tr>
			</c:forEach>
			</c:if>
			<tr>
				<td colspan="5"><h3>总计: ￥${shoppingCart.totalResult()/100} </h3></td>							
			</tr>
		</table>
			<hr>
	</div>
	<!-- 添加地址 -->
	<div>
		<%-- <a href="${contextPath }/uc/add-address">【新建收货地址】</a> --%>		
		<form:form action="" method="post" commandName="orderForm">
		<sec:csrfInput/>		
		    <div>
		      <label for="shippingAddressID"><h4>收货人地址列表</h4></label>
		      <form:select path="shippingAddressID">		      
		      	<form:option value="">--请选择收货地址--</form:option>          		
		        <c:forEach items="${addresses }" var="addr">
						<form:option value="${addr.ship_id }">
							${addr.consignee }-${addr.tel }-${addr.address }
						</form:option>											
				</c:forEach>		        
		      </form:select>
		      <form:errors path="shippingAddressID" cssClass="add-error"></form:errors>
		    </div>
				<button type="submit">提交订单</button>		
		</form:form>		
	</div>
</t:layout>