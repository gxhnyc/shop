<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"></c:set> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div>
		  <form:form action="${contextPath }/uc/add-address" method="post" commandName="shippingAddress">
		  	<sec:csrfInput/>
		    <div>
		      <label for="consignee">收件人：</label>
		      <form:input type="text" path="consignee" />
		      <form:errors path="consignee" cssClass="add-error"></form:errors>
		    </div>
		    
		    <div>
		      <label for="tel">联系电话</label>
		        <form:input type="text" path="tel" />
		         <form:errors path="tel" cssClass="add-error"></form:errors>
		    </div>
		    
		    <div>
		      <label for="address">详细地址</label>
		        <form:input type="text" path="address" />
		         <form:errors path="address" cssClass="add-error"></form:errors>
		    </div>    
	
		    <div>
		      <button type="submit">添加</button>
		    </div>
  	</form:form>
  	</div>
</body>
</html>