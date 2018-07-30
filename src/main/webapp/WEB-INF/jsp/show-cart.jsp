<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath }"></c:set>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
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
                	<input type="number" min="1" value="${item.amount}"
								class="item-amount" data-cellphone-id="${item.cellphone.cp_id}">  
              	</td>
				
				
				
				<%-- <td>
					<form action="${contextPath}/uc/shopping-cart/item-dec" method="post" class="inline">
			            <sec:csrfInput />
			            <input type="hidden" name="cp_id" value="${item.cellphone.cp_id}">
			            <button type="submit">-</button>
		          	</form>        
		          	${item.amount}
		          	<form action="${contextPath}/uc/shopping-cart/item-inc" method="post" class="inline">
			            <sec:csrfInput />
			            <input type="hidden" name="cp_id" value="${item.cellphone.cp_id}">
			            <button type="submit">+</button>
		          	</form>          
        		</td> --%>
       	
				<td>
					<form action="${contextPath }/uc/remove-item" method="post">
						<sec:csrfInput />
						<input type="hidden" name="cp_id" value="${item.cellphone.cp_id}">
						<input type="submit" value="删除">
					</form>
				</td>
			</tr>
			</c:forEach>
			<hr>
			<tr>
			
				<td colspan="5">总金额: ￥
					
					<span id="totalCost">${shoppingCart.totalResult() / 100}</span>
				
					<a href="${contextPath }/uc/create-order">【创建订单】</a>
				</td>
				
			</tr>
			</c:if>
		</table>
			
	</div>	
	
</jsp:body>
</t:layout>

