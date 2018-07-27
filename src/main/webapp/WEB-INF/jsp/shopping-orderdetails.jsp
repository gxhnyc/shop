<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"></c:set>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
<title>订单详情</title>
<style type="text/css">
	body{
		background:pink;
		text-align:center;
	}
	div{margin:5px 350px;}
	ul{
		float:left;
		list-style-type:none;
	}
	a{
		text-decoration:none;
	}
	a:hover{
		color:red;
	}
	.inline{
		display:inline;
	}
</style>


</head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<body>
	<div>
		<h2>我的订单</h2>
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
				<td>
					<form action="${contextPath }/uc/showorders" method="post">
						<sec:csrfInput/>
						<input type="submit" value="结算">
					</form>
				</td>
			</tr>
		</table>
		
	</div>
	
	
	<div>
		<hr>
		@Copyright 2008-2028<br/>
		<strong>三头牛科技有限公司</strong>
	
	</div>
</body>
</html>