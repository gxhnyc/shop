<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"></c:set>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:layout title="手机详细信息">
	<div>		
		<h4>
			<a href="${contextPath }/">【返回首页】</a>
			<a href="${contextPath }/uc/showcart">【查看购物车】</a>
		</h4>
	</div>
	<div>
		<ul>
			<li><img src="${contextPath }/${cellphone.image}" alt="iphone10" ></li>
		</ul>
		<ul style="margin-top:100px;">	  		
	        <li>品牌：${cellphone.cp_brand}</li>
	        <li>型号：${cellphone.cp_model}</li>
	        <li>系统：${cellphone.cp_os}</li>
	        <li>CPU:${cellphone.cp_cpu} ${cellphone.cp_cpu_cores}核</li>
	        <li>内存：${cellphone.cp_ram}GB</li>
	        <li>容量：${cellphone.cp_storage}GB</li>
	        <li>颜色：${cellphone.cp_color}</li>
	        <li>价格：￥${cellphone.cp_price/100}</li>
	        <li><textarea rows="6" cols="30">${cellphone.cp_description}</textarea> </li>
			<li>
				<form action="${contextPath }/uc/addcart" method="post">
					<sec:csrfInput/>
					<input type="hidden" name="cp_id" value="${cellphone.cp_id }">
					<input type="hidden" name="amount" value="1">
					<input type="submit" value= "加入购物车">
				
				</form>				
			</li>	    
  		</ul>	
	</div>

</t:layout>

	
	<!-- <div style="clear:both">
		<hr>
		@Copyright 2008-2028<br/>
		<strong>三头牛科技有限公司</strong>
	
	</div> -->
