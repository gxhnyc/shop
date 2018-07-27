<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"></c:set>   
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%-- 调用布局，内容将插入布局的content区域 --%>
<t:layout title="修改收货地址">
  	<div>
		<form:form action="" method="post" commandName="shippingAddress">
		    
		    <div>
				<label for="consignee">收件人：</label>
				<form:input type="text" path="consignee" id="consignee" />	
				<form:errors path="consignee" cssClass="add-error"></form:errors>		
			</div>	
			<div>
				<label for="tel">电　话：</label>
				<form:input type="text" path="tel" id="tel"/>	
				<form:errors path="tel" cssClass="add-error"></form:errors>			
			</div>
		    <div>
				<label for="address">地　址：</label>
				<form:input type="text" path="address" id="address"/>			
			</div>
		    
		    <div>
		      <button type="submit">修改</button>
		    </div>
  		</form:form>
  	</div>
  	
</t:layout>

